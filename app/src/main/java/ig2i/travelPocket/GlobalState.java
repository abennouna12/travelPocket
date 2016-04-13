package ig2i.travelPocket;

        import android.app.Application;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.lang.reflect.Type;
        import java.util.ArrayList;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;


/**
 * Created by aBennouna on 06/03/2016.
 */
public class GlobalState extends Application{
    static String cat = "MyTravelPocketDEBUG";
    SharedPreferences prefs;
    Gson gson;

    @Override
    public void onCreate() {
        gson = new Gson();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loadCities();
        super.onCreate();
    }

    // Fonction pour afficher un toast sur le telephone et un log dans la console pour alerter
    public void alerter(String s) {
        Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
        t.show();
        Log.i(cat, s);
    }

    List<City> cities;
    Set<String> latlongs;
    City selectedCity;

    // Fonction lancée au démarrage de l'application pour initialiser les villes
    // (A modifier pour enregistrer la liste dans les system parameters)
    public void loadCities() {
        if(prefs.contains("cities")) {
            Type type = new TypeToken<List<City>>(){}.getType();
            cities = gson.fromJson(prefs.getString("cities", ""), type);
            //cities = new ArrayList<>();
            //alerter(prefs.getString("cities", ""));
        } else {
            cities = new ArrayList<>();
        }
        if(prefs.contains("latlongs")) {
            latlongs = prefs.getStringSet("latlongs", null);
        } else {
            latlongs = new HashSet<>();
        }

    }

    // Fonction random permet de retourner un int entre deux valeurs prédéfinis
    // Cette fonction est principalement utilisée lors de la récupération des images
    // de l'API Flickr, on choisis une image random
    public int random(int min, int max) {
        return min + (int)(Math.random() * max);
    }

    public String translateDay (String engDay)
    {
        switch (engDay){
            default :
                return "";
            case "Mon" :
                return "Lun";
            case "Tue" :
                return "Mar";
            case "Wed" :
                return "Mer";
            case "Thu" :
                return "Jeu";
            case "Fri" :
                return "Ven";
            case "Sat" :
                return "Sam";
            case "Sun" :
                return "Dim";
        }
    }

}
