package ig2i.travelPocket.service;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.activity.MainActivity;
import ig2i.travelPocket.model.City;
import ig2i.travelPocket.model.PictureInfo;
import ig2i.travelPocket.model.WeatherDetail;


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

    public JSONWeatherInfo(GlobalState gs, String param, String latlong, MainActivity obj, String name, String pays) {
        this.param = param;
        this.latlong = latlong;
        this.obj = obj;
        this.name = name;
        this.gs = gs;
        this.pays = pays;
        result = new City();
    }

    @Override
    protected City doInBackground(Void... params) {


        String WeatherURL = "https://api.forecast.io/forecast/33e15e6da3e6f7c501e82e70461c5163/" +
                latlong +
                "?lang=fr" +
                "&units=si" +
                "&exclude=minutely,hourly";

        WeatherResult = WeatherParser.getJSONFromUrl(WeatherURL);


        try {

            int maxPictures = 0;

            String currentWeather = WeatherResult
                    .getJSONObject("currently")
                    .getString("temperature");

            // Arrondir la meteo
            int finalWeather = (int) Math.round((Double.valueOf(currentWeather) * 1 / 1));

            result.currentWeather = Integer.toString(finalWeather) + "°";

            result.daily = new ArrayList<>();

            JSONArray daily = WeatherResult
                    .getJSONObject("daily")
                    .getJSONArray("data");

            // Ne prendre que les 5 previsions incluant celle du jour, l'API nous fournit parfois
            // les previsions de la veille, pour cela nous allons faire une verification

            Date now = new Date();
            Date firstDateApi =  new java.util.Date(daily.getJSONObject(0).getLong("time") * 1000);
            int startday = (now.getDay() - firstDateApi.getDay());

            for (int i = startday; i < startday + 5; ++i) {

                JSONObject day = daily.getJSONObject(i);
                WeatherDetail wd = new WeatherDetail();
                wd.date = gs.translateDay(new java.util.Date(day.getLong("time") * 1000)
                        .toString().split(" ")[0]);

                wd.icon = "wi_" + day.getString("icon").replace("-", "_");

                Date sunset = new Date(day.getLong("sunsetTime") * 1000);

                // Nous modifions l'icon de la meteo dependamment du coucher et lever de soleil

                Calendar c = Calendar.getInstance();
                c.setTime(now);
                c.add(Calendar.DATE, i);
                now = c.getTime();

                if (now.after(sunset)) {
                    wd.icon = wd.icon.replace("day", "night");
                } else {
                    wd.icon = wd.icon.replace("night", "day");
                }

                Double temperatureMin = Double.valueOf(day.getString("temperatureMin"));
                String weatherMin = Integer.toString((int) Math.round((temperatureMin * 1 / 1))) + "°";

                Double temperatureMax = Double.valueOf(day.getString("temperatureMax"));
                String weatherMax = Integer.toString((int) Math.round((temperatureMax * 1 / 1))) + "°";

                wd.tempMin = weatherMin;
                wd.tempMax = weatherMax;
                result.daily.add(wd);
            }

            if(param.equals("add") || gs.isPhotosUpdatable()) {

                String FlickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                        "group_id=1463451%40N25&view_all=1" +
                        "&lat=" + latlong.split(",")[0] +
                        "&lon=" + latlong.split(",")[1] +
                        "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027" +
                        "&format=json";

                FlickrResult = FlickrParser.getJSONFromUrlFlickr(FlickrURL);
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

                if (total > 0) {

                    photos = obj.getJSONArray("photo");
                    result.pictures = new ArrayList<>();

                    maxPictures = (photos.length() <= 5) ? photos.length() : 5;

                    for(int i = 0 ; i < maxPictures ; i++) {

                        photo = photos.getJSONObject(i);

                        // Recuperer les données de l'image
                        id = photo.getString("id");
                        secret = photo.getString("secret");
                        server = photo.getString("server");
                        farm = photo.getString("farm");


                        PictureInfo picture = new PictureInfo();

                        String photoDetail = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo" +
                                "&photo_id=" + id +
                                "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027&format=json";


                        JSONObject pictureDetails = FlickrParser.getJSONFromUrlFlickr(photoDetail).getJSONObject("photo");

                        picture.author = pictureDetails.getJSONObject("owner").getString("username");
                        picture.description = pictureDetails.getJSONObject("description").getString("_content");
                        picture.takenOn = new Date(pictureDetails.getJSONObject("dates").getLong("posted")*1000);
                        picture.src = "https://farm" + farm + ".staticflickr.com/"
                                + server + "/" + id + "_" + secret + ".jpg";

                        result.pictures.add(picture);

                    }


                } else {

                     FlickrURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&" +
                            "&lat=" + latlong.split(",")[0] +
                            "&lon=" + latlong.split(",")[1] +
                            "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027" +
                            "&format=json";

                    FlickrResult = FlickrParser.getJSONFromUrlFlickr(FlickrURL);

                    obj = FlickrResult.getJSONObject("photos");

                    photos = obj.getJSONArray("photo");

                    maxPictures = (photos.length() <= 5) ? photos.length() : 5;

                    result.pictures = new ArrayList<>();

                    for(int i = 0 ; i < maxPictures ; i++) {

                        photo = photos.getJSONObject(i);

                        // Recuperer les données de l'image
                        id = photo.getString("id");
                        secret = photo.getString("secret");
                        server = photo.getString("server");
                        farm = photo.getString("farm");

                        PictureInfo picture = new PictureInfo();

                        String photoDetail = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo" +
                                "&photo_id=" + id +
                                "&api_key=7c3be4ef9c1bc1c2a8c55609c72e2027&format=json";


                        JSONObject pictureDetails = FlickrParser.getJSONFromUrlFlickr(photoDetail).getJSONObject("photo");

                        if(pictureDetails.getJSONObject("owner").has("username")) {
                            picture.author = pictureDetails.getJSONObject("owner").getString("username");
                        } else {
                            picture.author = "";
                        }

                        if(pictureDetails.getJSONObject("description").has("_content")) {
                            picture.description = pictureDetails.getJSONObject("description").getString("_content");
                        } else {
                            picture.description = "";
                        }

                        if(pictureDetails.getJSONObject("dates").has("posted")) {
                            picture.takenOn = new Date(pictureDetails.getJSONObject("dates").getLong("posted")*1000);
                        } else {
                            picture.takenOn = null;
                        }

                        picture.src = "https://farm" + farm + ".staticflickr.com/"
                                + server + "/" + id + "_" + secret + ".jpg";

                        result.pictures.add(picture);

                    }

                }
            }

            result.pays = pays;
            result.name = name;
            result.description = WeatherResult.getJSONObject("currently").getString("summary");
            result.latitude = latlong.split(",")[0];
            result.longitude = latlong.split(",")[1];

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(City result) {
        if ( param.equals("add") ) {
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