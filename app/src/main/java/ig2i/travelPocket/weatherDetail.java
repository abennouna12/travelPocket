package ig2i.travelPocket;

/**
 * Created by aBennouna on 21/03/2016.
 */
public class WeatherDetail {
    // Classe d'ajout de villes
    String date;
    String tempMin;
    String tempMax;
    String icon;

    // Constructeur
    WeatherDetail(String date, String tempMax, String tempMin, String icon) {
        this.date = date;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.icon = icon;
    }

    WeatherDetail(){

    }
}
