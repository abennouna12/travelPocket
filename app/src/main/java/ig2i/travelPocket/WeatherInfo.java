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


        String WeatherURL = "http://api.openweathermap.org/data/2.5/weather?" +
                latlong +
                "&appid=b55ae43162650bcdf1c1e764d51ab083" +
                "&units=metric";

        String FlickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                "group_id=1463451%40N25&view_all=1" +
                "&text=" + city +
                "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027" +
                "&format=json";

        WeatherResult = WeatherParser.getJSONFromUrl(WeatherURL);

        FlickrResult = FlickrParser.getJSONFromUrlFlickr(FlickrURL);

        try {
            JSONObject main = WeatherResult.getJSONObject("main");
            Double initWeather = new Double(main.getString("temp"));
            int finalWeather = (int)Math.round(initWeather * 1) / 1;
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

            String description = WeatherResult
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");

            int weatherID = WeatherResult
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getInt("id");

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
                        "&text=" + description +
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
            result.add(gs.getWeatherDescription(weatherID));

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