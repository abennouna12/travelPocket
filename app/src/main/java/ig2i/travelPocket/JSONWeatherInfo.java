package ig2i.travelPocket;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aBennouna on 13/03/2016.
 */
public class JSONWeatherInfo extends AsyncTask<Void, Void, City> {

    GlobalState gs;
    String latlong;
    String name;
    String param;
    MainActivity obj;
    JSONParser WeatherParser = new JSONParser();
    JSONObject WeatherResult;
    JSONParser FlickrParser = new JSONParser();
    JSONObject FlickrResult;
    City result;
    String pays;

    public JSONWeatherInfo(String param, String latlong, MainActivity obj, String name, String pays)
    {
        this.param = param;
        this.latlong = latlong;
        this.obj = obj;
        this.name = name;
        gs = new GlobalState();
        this.pays = pays;
        result = new City();
    }

    @Override
    protected City doInBackground(Void... params) {

        if(param == "add") {

            String WeatherURL = "https://api.forecast.io/forecast/33e15e6da3e6f7c501e82e70461c5163/" +
                    latlong +
                    "?lang=fr" +
                    "&units=si" +
                    "&exclude=minutely,hourly";

            String FlickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                    "group_id=1463451%40N25&view_all=1" +
                    "&text=" + name +
                    "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027" +
                    "&format=json";

            WeatherResult = WeatherParser.getJSONFromUrl(WeatherURL);

            FlickrResult = FlickrParser.getJSONFromUrlFlickr(FlickrURL);

            try {

                String currentWeather = WeatherResult
                        .getJSONObject("currently")
                        .getString("temperature");

                Double initWeather = new Double(currentWeather);
                int finalWeather = (int) Math.round((initWeather * 1 / 1));

                result.currentWeather = Integer.toString(finalWeather) + "°";

                result.daily = new ArrayList<>();

                JSONArray daily = WeatherResult
                        .getJSONObject("daily")
                        .getJSONArray("data");

                for (int i = 0; i < 5; ++i) {
                    JSONObject day = daily.getJSONObject(i);
                    WeatherDetail wd = new WeatherDetail();
                    wd.date = gs.translateDay(new java.util.Date(day.getLong("time") * 1000)
                            .toString().split(" ")[0]);

                    wd.icon = "wi_" + day.getString("icon").replace("-", "_");

                    Date sunset = new Date(day.getLong("sunsetTime") * 1000);

                    Date now = new Date();

                    Calendar c = Calendar.getInstance();
                    c.setTime(now);
                    c.add(Calendar.DATE, i);
                    now = c.getTime();

                    if (now.after(sunset)) {
                        wd.icon = wd.icon.replace("day", "night");
                    } else {
                        wd.icon = wd.icon.replace("night", "day");
                    }

                    Double temperatureMin = new Double(day.getString("temperatureMin"));
                    String weatherMin = Integer.toString((int) Math.round((temperatureMin * 1 / 1))) + "°";

                    Double temperatureMax = new Double(day.getString("temperatureMax"));
                    String weatherMax = Integer.toString((int) Math.round((temperatureMax * 1 / 1))) + "°";

                    wd.tempMin = weatherMin;
                    wd.tempMax = weatherMax;
                    result.daily.add(wd);
                }

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

                String description = WeatherResult
                        .getJSONObject("currently")
                        .getString("summary");


                if (total > 0) {

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

                result.pays = pays;
                result.name = name;

                result.picture = "https://farm" + farm + ".staticflickr.com/"
                        + server + "/" + id + "_" + secret + ".jpg";

                result.description = description;
                result.latitude = latlong.split(",")[0];
                result.longitude = latlong.split(",")[1];

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            String WeatherURL = "https://api.forecast.io/forecast/33e15e6da3e6f7c501e82e70461c5163/" +
                    latlong +
                    "?lang=fr" +
                    "&units=si" +
                    "&exclude=minutely,hourly";

            WeatherResult = WeatherParser.getJSONFromUrl(WeatherURL);

            try {

                String currentWeather = WeatherResult
                        .getJSONObject("currently")
                        .getString("temperature");

                String description = WeatherResult
                        .getJSONObject("currently")
                        .getString("summary");

                Double initWeather = new Double(currentWeather);
                int finalWeather = (int) Math.round((initWeather * 1 / 1));

                result.currentWeather = Integer.toString(finalWeather) + "°";

                result.daily = new ArrayList<>();

                JSONArray daily = WeatherResult
                        .getJSONObject("daily")
                        .getJSONArray("data");

                for (int i = 0; i < 5; ++i) {
                    JSONObject day = daily.getJSONObject(i);
                    WeatherDetail wd = new WeatherDetail();
                    wd.date = gs.translateDay(new java.util.Date(day.getLong("time") * 1000)
                            .toString().split(" ")[0]);

                    wd.icon = "wi_" + day.getString("icon").replace("-", "_");

                    Date sunset = new Date(day.getLong("sunsetTime") * 1000);

                    Date now = new Date();

                    Calendar c = Calendar.getInstance();
                    c.setTime(now);
                    c.add(Calendar.DATE, i);
                    now = c.getTime();

                    if (now.after(sunset)) {
                        wd.icon = wd.icon.replace("day", "night");
                    } else {
                        wd.icon = wd.icon.replace("night", "day");
                    }

                    Double temperatureMin = new Double(day.getString("temperatureMin"));
                    String weatherMin = Integer.toString((int) Math.round((temperatureMin * 1 / 1))) + "°";

                    Double temperatureMax = new Double(day.getString("temperatureMax"));
                    String weatherMax = Integer.toString((int) Math.round((temperatureMax * 1 / 1))) + "°";

                    wd.tempMin = weatherMin;
                    wd.tempMax = weatherMax;
                    result.daily.add(wd);
                }

                result.pays = pays;
                result.name = name;

                result.picture = "";

                result.description = description;
                result.latitude = latlong.split(",")[0];
                result.longitude = latlong.split(",")[1];

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    @Override
    protected void onPostExecute(City result) {
        if ( param == "add" ) {
            this.obj.setWeather(result);
            this.obj.addCity();
        } else {
            this.obj.update(result);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

}