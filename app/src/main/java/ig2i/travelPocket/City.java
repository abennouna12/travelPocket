package ig2i.travelPocket;

import java.util.ArrayList;
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
    List<Suggestion> suggestions;

    // Constructeur
    City(String pays, String name, String description, String currentWeather, String picture) {
        this.pays = pays;
        this.description = description;
        this.name = name;
        this.currentWeather = currentWeather;
        this.picture = picture;
        //this.suggestions = new ArrayList<>();
        //this.daily = new ArrayList<>();
    }

    City(){
        //this.suggestions = new ArrayList<>();
    }

    void update(City c,Boolean updatePhoto)
    {
        pays = c.pays;
        name = c.name;
        currentWeather = c.currentWeather;
        if (updatePhoto) {
            picture = c.picture;
        }
        description = c.description;
        latitude = c.latitude;
        longitude = c.longitude;
        daily = c.daily;
    }



}
