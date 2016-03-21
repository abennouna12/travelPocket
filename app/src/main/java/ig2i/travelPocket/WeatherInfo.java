package ig2i.travelPocket;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aBennouna on 13/03/2016.
 */
public class WeatherInfo extends AsyncTask<Void, Void, List<String>> {

    GlobalState gs;
    String latlong;
    String city;
    MainActivity obj;
    JSONParser WeatherParser = new JSONParser();
    JSONObject WeatherResult;
    JSONParser FlickrParser = new JSONParser();
    JSONObject FlickrResult;
    List<String> result;

    public WeatherInfo(String latlong, MainActivity obj, String city)
    {
        this.latlong = latlong;
        this.obj = obj;
        this.city = city;
        gs = new GlobalState();
        result = new ArrayList<>();
    }

    @Override
    protected List<String> doInBackground(Void... params) {


        String WeatherURL = "https://api.forecast.io/forecast/33e15e6da3e6f7c501e82e70461c5163/" +
                latlong +
                "?lang=fr" +
                "&units=si" +
                "&exclude=minutely,hourly";

        String FlickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                "group_id=1463451%40N25&view_all=1" +
                "&text=" + city +
                "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027" +
                "&format=json";

        WeatherResult = WeatherParser.getJSONFromUrl(WeatherURL);

        FlickrResult = FlickrParser.getJSONFromUrlFlickr(FlickrURL);

        try {

            String currentWeather  = WeatherResult
                    .getJSONObject("currently")
                    .getString("temperature");

            Double initWeather = new Double(currentWeather);
            int finalWeather = (int)Math.ceil(initWeather);

            result.add(Integer.toString(finalWeather));

            // Recuperer toutes les photos et ne choisir qu'une seule
            JSONObject obj;
            JSONArray photos;
            JSONObject photo;

            // Recuperer les données de l'image
            String id;
            String secret;
            String server;
            String farm;
            obj = FlickrResult.getJSONObject("photos");
            int total = obj.getInt("total");

            String icon = WeatherResult
                    .getJSONObject("currently")
                    .getString("icon")
                    .replace("-", " ");

            String description  = WeatherResult
                    .getJSONObject("currently")
                    .getString("summary");


            if (total>0) {

                photos = obj.getJSONArray("photo");
                photo = photos.getJSONObject(gs.random(0, photos.length()));

                // Recuperer les données de l'image
                id = photo.getString("id");
                secret = photo.getString("secret");
                server = photo.getString("server");
                farm = photo.getString("farm");


            } else {

                FlickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                        "group_id=1463451%40N25&view_all=1" +
                        "&text=" + icon +
                        "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027" +
                        "&format=json";

                FlickrResult = FlickrParser.getJSONFromUrlFlickr(FlickrURL);

                obj = FlickrResult.getJSONObject("photos");

                photos = obj.getJSONArray("photo");
                photo = photos.getJSONObject(gs.random(0, photos.length()));

                // Recuperer les données de l'image
                id = photo.getString("id");
                secret = photo.getString("secret");
                server = photo.getString("server");
                farm = photo.getString("farm");
            }

            result.add("https://farm" + farm + ".staticflickr.com/"
                    + server + "/" + id + "_" + secret + ".jpg");
            result.add(description);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        this.obj.setWeather(result);
        this.obj.addCity();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}