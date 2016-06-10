package ig2i.travelPocket.service;
import ig2i.travelPocket.R;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.activity.InfoCityActivity;
import ig2i.travelPocket.model.City;
import ig2i.travelPocket.model.Suggestion;

public class JSONSuggestions extends AsyncTask<Void, Integer, City> {

    GlobalState gs;
    InfoCityActivity obj;
    JSONParser SuggestionsParser = new JSONParser();
    JSONObject SuggestionsResult;
    JSONParser PlaceParser = new JSONParser();
    JSONParser ImageParser = new JSONParser();
    JSONObject PlaceResult;
    JSONObject ImageResult;
    City result;
    Gson gson;

    public JSONSuggestions(InfoCityActivity obj)
    {
        this.obj = obj;
        this.gs = obj.gs;
        this.result = obj.gs.selectedCity;
        gson = new Gson();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        obj.loaderSuggestion.setVisibility(View.VISIBLE);
        obj.imageViewPagerEmpty.setVisibility(View.GONE);
    }

    @Override
    protected City doInBackground(Void... params) {

        publishProgress();
        int maxSuggestions = Integer.decode(gs.prefs.getString("maxSuggestions", "20")) - 1;
        int radius = Integer.decode(gs.prefs.getString("radius", "1")) * 1000;


        String suggestionsURL = "https://maps.googleapis.com/maps/api/place/radarsearch/json?" +
                "location=" + result.latitude + ","+ result.longitude +
                "&radius=" + radius +
                "&types=" + gs.getTypesStringValues() +
                "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";

        SuggestionsResult = SuggestionsParser.getJSONFromUrl(suggestionsURL);

        try {

            result.suggestions = new ArrayList<>();

            if(SuggestionsResult!=null) {
                if(SuggestionsResult.has("status")) {
                    if(SuggestionsResult.getString("status").equals("OK")) {

                        JSONArray suggestions = SuggestionsResult.getJSONArray("results");


                        // Si la liste des suggestions fournie par google contient moins d'elements
                        // que le nombre de suggestions voulues, on ne fourni que le nombre de
                        // suggestions possibles
                        maxSuggestions = (suggestions.length() < maxSuggestions) ?
                                suggestions.length() : maxSuggestions;

                        for(int i = 0 ; i < maxSuggestions ; i++) {

                            JSONObject suggest = suggestions.getJSONObject(i);
                            String placeId = suggest.getString("place_id");
                            Suggestion s = new Suggestion();
                            if(gs.prefs.contains("Suggestion:" + placeId)) {
                                Type type = new TypeToken<Suggestion>(){}.getType();
                                s = gson.fromJson(gs.prefs.getString("Suggestion:" + placeId, "")
                                        , type);
                                result.suggestions.add(s);
                            } else {

                                s.latitude = suggest.getJSONObject("geometry")
                                        .getJSONObject("location").getString("lat");
                                s.longitude = suggest.getJSONObject("geometry")
                                        .getJSONObject("location").getString("lng");

                                // On lance une requete pour pouvoir avoir les details d'une
                                // suggestion Tel / Image / Site ...

                                String placeLink =
                                        "https://maps.googleapis.com/maps/api/place/details/json?"
                                        + "placeid=" + placeId
                                        + "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";

                                PlaceResult = PlaceParser.getJSONFromUrl(placeLink)
                                        .getJSONObject("result");

                                if (PlaceResult.has("opening_hours")) {
                                    s.openClose = (PlaceResult.getJSONObject("opening_hours")
                                            .getBoolean("open_now")) ? "Ouvert" : "Fermé";
                                } else {
                                    s.openClose = "NC";
                                }

                                s.placeName = PlaceResult.getString("name");

                                if (PlaceResult.has("photos")) {
                                    s.picture = "https://maps.googleapis.com/maps/api/place/photo?"
                                            + "maxwidth=400&photoreference="
                                            + PlaceResult.getJSONArray("photos")
                                                    .getJSONObject(gs.random(0,
                                                            PlaceResult.getJSONArray("photos")
                                                                    .length()))
                                                    .getString("photo_reference")
                                            + "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";

                                } else {
                                    // Si google places ne nous fournit pas de photos, on récupere
                                    // la photo de la meteo

                                    String address = result.name + " " + result.pays;

                                    String imageUrl = "https://www.googleapis.com/customsearch/v1?"
                                            + "searchType=image"
                                            + "&imgSize=medium"
                                            + "&cx=007618608112391347465:01jqorh7r9w"
                                            + "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw"
                                            //+ "&key=AIzaSyBXNpZDgx5lQU3iqNn4T6sAI0JgWWlxkXw"
                                            + "&q=" + PlaceResult.getString("name") + " "
                                            + address;


                                    ImageResult = ImageParser.getJSONFromUrl(imageUrl
                                            .replace(" ", "%20"));
                                    if(ImageResult != null) {
                                        if (!ImageResult.has("error")) {
                                            if (ImageResult.has("items")) {
                                                int imagePosition = 0;
                                                do
                                                {
                                                    imagePosition++;
                                                } while (ImageResult.getJSONArray("items")
                                                        .getJSONObject(imagePosition).getString("link")
                                                        .toUpperCase().contains("MAP"));
                                                s.picture = ImageResult.getJSONArray("items")
                                                        .getJSONObject(imagePosition).getString("link");
                                            }
                                        }
                                    } else {
                                        s.picture = result.pictures.get(gs.random(0,
                                                result.pictures.size())).src;
                                    }


                                }

                                s.phone = (PlaceResult.has("international_phone_number")) ?
                                        PlaceResult.getString("international_phone_number") : "";

                                s.web = (PlaceResult.has("website")) ?
                                        PlaceResult.getString("website") : "";

                                s.rating = (PlaceResult.has("rating")) ?
                                        PlaceResult.getLong("rating") : (float) 0;

                                // Si le nom n'est constitué que de chiffres (exemple code postale),
                                // on n'affiche pas la suggestion
                                try {
                                    double d = Double.parseDouble(s.placeName);
                                    maxSuggestions = maxSuggestions +
                                            (int) Math.round(d) - (int) Math.round(d);
                                    maxSuggestions = (maxSuggestions < suggestions.length()) ?
                                            maxSuggestions + 1 : maxSuggestions;
                                } catch (NumberFormatException nfe) {
                                    result.suggestions.add(s);
                                    SharedPreferences.Editor editor = gs.prefs.edit();
                                    editor.putString("Suggestion:" + placeId, gson.toJson(s));
                                    editor.apply();
                                }
                            }
                        }

                    }
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(City result) {
        // On renvoie la liste des suggestions a l'activité pour être traitée
        obj.addSuggestions(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}