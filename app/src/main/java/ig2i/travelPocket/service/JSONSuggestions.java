package ig2i.travelPocket.service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.activity.InfoCityActivity;
import ig2i.travelPocket.model.City;
import ig2i.travelPocket.model.Suggestion;

public class JSONSuggestions extends AsyncTask<Void, Void, City> {

    GlobalState gs;
    InfoCityActivity obj;
    JSONParser SuggestionsParser = new JSONParser();
    JSONObject SuggestionsResult;
    JSONParser PlaceParser = new JSONParser();
    JSONObject PlaceResult;
    City result;

    public JSONSuggestions(InfoCityActivity obj)
    {
        this.obj = obj;
        this.gs = obj.gs;
        this.result = obj.gs.selectedCity;
    }

    @Override
    protected City doInBackground(Void... params) {

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

            if(SuggestionsResult.getString("status").equals("OK")) {

                JSONArray suggestions = SuggestionsResult.getJSONArray("results");


                // Si la liste des suggestions fournie par google contient moins d'elements que
                // le nombre de suggestions voulues, on ne fourni que le nombre de suggestions
                // possibles
                maxSuggestions = (suggestions.length() < maxSuggestions) ? suggestions.length() : maxSuggestions;

                for(int i = 0 ; i < maxSuggestions ; i++) {

                    JSONObject suggest = suggestions.getJSONObject(i);


                    Suggestion s = new Suggestion();

                    s.latitude = suggest.getJSONObject("geometry").getJSONObject("location").getString("lat");
                    s.longitude = suggest.getJSONObject("geometry").getJSONObject("location").getString("lng");

                    // On lance une requete pour pouvoir avoir les details d'une suggestion
                    // Tel / Image / Site ...

                    String placeLink = "https://maps.googleapis.com/maps/api/place/details/json?" +
                            "placeid=" + suggest.getString("place_id") +
                            "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";

                    PlaceResult = PlaceParser.getJSONFromUrl(placeLink).getJSONObject("result");

                    if(PlaceResult.has("opening_hours")) {
                        s.openClose = (PlaceResult.getJSONObject("opening_hours")
                                .getBoolean("open_now")) ? "Ouvert" : "Fermé";
                    } else {
                        s.openClose = "NC";
                    }

                    s.placeName = PlaceResult.getString("name");

                    if(PlaceResult.has("photos")) {
                        s.picture = "https://maps.googleapis.com/maps/api/place/photo?" +
                                "maxwidth=400&photoreference=" +
                                PlaceResult.getJSONArray("photos")
                                        .getJSONObject(gs.random(0,PlaceResult.getJSONArray("photos").length()))
                                        .getString("photo_reference") +
                                "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";

                    } else {
                        // Si google places ne nous fournit pas de photos, on récupere la photos de
                        // la meteo
                        s.picture = result.pictures.get(0).src;
                    }

                    s.phone = (PlaceResult.has("international_phone_number")) ?
                            PlaceResult.getString("international_phone_number") : "";

                    s.web = (PlaceResult.has("website")) ?
                            PlaceResult.getString("website") : "";

                    s.rating = (PlaceResult.has("rating")) ?
                            PlaceResult.getLong("rating") : (float)0;

                    // Si le nom n'est constitué que de chiffres (exemple code postale), en n'affiche
                    // pas la suggestion
                    try
                    {
                        double d = Double.parseDouble(s.placeName);
                        maxSuggestions = (maxSuggestions < suggestions.length()) ?
                                maxSuggestions + 1 : maxSuggestions;
                    }
                    catch(NumberFormatException nfe)
                    {
                        result.suggestions.add(s);
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