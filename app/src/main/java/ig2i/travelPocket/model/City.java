package ig2i.travelPocket.model;

import java.util.List;

public class City {
    // Classe d'ajout de villes
    public String pays;
    public String name;
    public String currentWeather;
    public List<PictureInfo> pictures;
    public String description;
    public String latitude;
    public String longitude;
    public List<WeatherDetail> daily;
    public List<Suggestion> suggestions;

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

    public City(){
        //this.suggestions = new ArrayList<>();
    }

    // Mise a jour des informations d'une ville
    // Afin d'éviter un long temps de chargemen, l'utilisateur pourra choisir de mettre à jour les
    // photos ou non a partir des parametres
    public void update(City c,Boolean photoUpdatable)
    {
        pays = c.pays;
        name = c.name;
        currentWeather = c.currentWeather;
        if (photoUpdatable) {
            pictures = c.pictures;
        }
        description = c.description;
        latitude = c.latitude;
        longitude = c.longitude;
        daily = c.daily;
    }



}
