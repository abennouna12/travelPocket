package ig2i.travelPocket.model;

import java.util.ArrayList;
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



    public City(){
        this.suggestions = new ArrayList<>();
        this.pictures = new ArrayList<>();
    }

    /**
     * Mise a jour des informations d'une ville
     * Afin d'éviter un long temps de chargement, l'utilisateur pourra choisir de mettre à jour
     * les photos ou non a partir des parametres
     * @param c La ville a mettre a jour
     * @param photoUpdatable Photo a mettre a jour ou non
     */
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
