package ig2i.travelPocket;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.location.LocationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by aBennouna on 13/03/2016.
 */
public class JSONSuggestions extends AsyncTask<Void, Void, City> {

    GlobalState gs;
    InfoCity obj;
    JSONParser SuggestionsParser = new JSONParser();
    JSONObject SuggestionsResult;
    JSONParser PlaceParser = new JSONParser();
    JSONObject PlaceResult;
    City result;

    public JSONSuggestions(InfoCity obj)
    {
        this.obj = obj;
        this.gs = obj.gs;
        this.result = obj.gs.selectedCity;
    }

    @Override
    protected City doInBackground(Void... params) {

        String types = "";
        int maxSuggestions = Integer.decode(gs.prefs.getString("maxSuggestions", "20")) - 1;
        int radius = Integer.decode(gs.prefs.getString("radius", "1")) * 1000;


        if(gs.prefs.contains("placesType")) {

            Set<String> typesSet = gs.prefs.getStringSet("placesType", null);

            if (typesSet != null) {
                for (String p : typesSet) {
                    types = types + "|" + p;
                }
            }
            types = (types.isEmpty()) ? types : types.substring(1);
        }

        String typeString = (types.equals(""))? "&types=all" : ("&types=" + types);

        String suggestionsURL = "https://maps.googleapis.com/maps/api/place/radarsearch/json?" +
                "location=" + result.latitude + ","+ result.longitude +
                "&radius=" + radius + typeString +
                "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";



        SuggestionsResult = SuggestionsParser.getJSONFromUrl(suggestionsURL);


        try {

            if(SuggestionsResult.getString("status").equals("OK")) {

                JSONArray suggestions = SuggestionsResult.getJSONArray("results");

                result.suggestions = new ArrayList<>();

                maxSuggestions = (suggestions.length() < maxSuggestions) ? suggestions.length() : maxSuggestions;

                for(int i = 0 ; i < maxSuggestions ; i++) {

                    JSONObject suggest = suggestions.getJSONObject(i);
                    Suggestion s = new Suggestion();
                    s.latitude = suggest.getJSONObject("geometry").getJSONObject("location").getString("lat");
                    s.longitude = suggest.getJSONObject("geometry").getJSONObject("location").getString("lng");

                    String placeLink = "https://maps.googleapis.com/maps/api/place/details/json?" +
                            "placeid=" + suggest.getString("place_id") +
                            "&key=AIzaSyD2qhsWZbP-bYq1URZEAaSY17NanvHOwbw";

                    PlaceResult = PlaceParser.getJSONFromUrl(placeLink).getJSONObject("result");

                    if(PlaceResult.has("opening_hours")) {
                        s.openClose = (PlaceResult.getJSONObject("opening_hours")
                                .getBoolean("open_now")) ? "Ouvert" : "Fermé";
                    } else {
                        s.openClose = "Non confirmé";
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
                        s.picture = result.picture;
                    }

                    s.phone = (PlaceResult.has("international_phone_number")) ?
                            PlaceResult.getString("international_phone_number") : "";

                    s.web = (PlaceResult.has("website")) ?
                            PlaceResult.getString("website") : "";

                    s.rating = (PlaceResult.has("rating")) ?
                            PlaceResult.getLong("rating") : (float)0;

                    result.suggestions.add(s);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(City result) {
        obj.addSuggestions(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}