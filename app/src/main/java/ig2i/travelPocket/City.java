package ig2i.travelPocket;

import java.util.List;

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
    /*
    City(String pays, String name, String description, String currentWeather, String picture) {
        this.pays = pays;
        this.description = description;
        this.name = name;
        this.currentWeather = currentWeather;
        this.picture = picture;
        //this.suggestions = new ArrayList<>();
        //this.daily = new ArrayList<>();
    }*/

    City(){
        //this.suggestions = new ArrayList<>();
    }

    // Mise a jour des informations d'une ville
    // Afin d'éviter un long temps de chargemen, l'utilisateur pourra choisir de mettre à jour les
    // photos ou non a partir des parametres
    void update(City c,Boolean photoUpdatable)
    {
        pays = c.pays;
        name = c.name;
        currentWeather = c.currentWeather;
        if (photoUpdatable) {
            picture = c.picture;
        }
        description = c.description;
        latitude = c.latitude;
        longitude = c.longitude;
        daily = c.daily;
    }



}
