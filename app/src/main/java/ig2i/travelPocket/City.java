package ig2i.travelPocket;

import java.util.List;

/**
 * Created by aBennouna on 11/03/2016.
 */
public class City {
    // Classe d'ajout de villes
    String pays;
    String name;
    String currentWeather;
    String picture;
    String description;
    String latitude;
    String longitude;
    List<WeatherDetail> daily;

    // Constructeur
    City(String pays, String name, String description, String currentWeather, String picture) {
        this.pays = pays;
        this.description = description;
        this.name = name;
        this.currentWeather = currentWeather;
        this.picture = picture;
    }

    City(){

    }

    void update(City c)
    {
        pays = c.pays;
        name = c.name;
        currentWeather = c.currentWeather;
        //picture = c.picture;
        description = c.description;
        latitude = c.latitude;
        longitude = c.longitude;
        daily = c.daily;
    }



}
