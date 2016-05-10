package ig2i.travelPocket;

        import android.app.Application;
        import android.content.SharedPreferences;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
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
        import ig2i.travelPocket.model.*;




public class GlobalState extends Application{
    public String tag = "MyTravelPocketDEBUG";
    public SharedPreferences prefs;
    public Gson gson;

    @Override
    public void onCreate() {
        gson = new Gson();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        loadCities();
        super.onCreate();
    }

    public List<City> cities;
    public Set<String> latlongs;
    public City selectedCity;

    // Fonction pour afficher un toast sur le telephone et un log dans la console pour alerter
    public void alerter(String s) {
        // Afficher un toast dans le telephone
        toast(s);
        // Afficher dans la console
        Log.i(tag, s);
    }

    // Fonction pour afficher un toast
    public void toast(String s) {
        Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
        t.show();
    }

    // Fonction lancée au démarrage de l'application pour initialiser les villes
    public void loadCities() {
        if(prefs.contains("cities")) {
            // On recupere la liste des villes des shared preferences pour ensuite la deserializer
            // en List<City>
            Type type = new TypeToken<List<City>>(){}.getType();
            cities = gson.fromJson(prefs.getString("cities", ""), type);
        } else {
            // Si il n'y a pas de liste enregistrée dans les shared preferences, on crée une liste
            cities = new ArrayList<>();
        }
        if(prefs.contains("latlongs")) {
            // On recupere la liste des latitudes et longitudes des shared preferences
            // Cette liste sert d'identifiant unique pour une ville
            latlongs = prefs.getStringSet("latlongs", null);
        } else {
            latlongs = new HashSet<>();
        }

    }

    // Fonction random permet de retourner un int entre deux valeurs prédéfinis
    // Cette fonction est principalement utilisée lors de la récupération des images
    // de l'API Flickr et des suggestions de Google Places, on choisis une image random
    public int random(int min, int max) {
        return min + (int)(Math.random() * max);
    }

    // Fonction permettant de traduire les jours obtenus par l'API Forecast.io (Anglais->Francais)
    public String translateDay (String engDay) {
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


    // Fonction permettant d'avoir les types de suggestions voulues
    public String getTypesList() {

        String pr = "";
        if(prefs.contains("placesType")) {

            Set<String> typesSet = prefs.getStringSet("placesType", null);

            if (typesSet != null) {
                for (String p : typesSet) {
                    pr = pr + "|" + p;
                }
            }

            pr = (pr.isEmpty()) ? "all" : pr.substring(1);
        }

        return pr;

    }

    // Cette fonction a pour but de creer un tag pour sauvegarder la liste des suggestions selon
    // les parametres choisis par l'utilisateur (Restaurant, Café, ....)
    // Ce tag est formé de telle sorte : Ville//param1|param2|param3...
    public String getParamSuggest() {
        return selectedCity.name + "//" + getTypesList();
    }

    public Boolean isSuggestionsRefreshable() {
        return prefs.getBoolean("refreshSuggestions",false);
    }

    public Boolean isPhotosUpdatable() {
        return prefs.getBoolean("updatePhoto",false);
    }

    public boolean isConnected()
    {
        // On vérifie si le réseau est disponible, si non on demande de verifier la connexion
        ConnectivityManager cnMngr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cnMngr.getActiveNetworkInfo();

        Boolean bStatut = false;
        if (netInfo != null) {
            NetworkInfo.State netState = netInfo.getState();
            if (netState.compareTo(NetworkInfo.State.CONNECTED) == 0) {
                bStatut = true;
            }
        }

        return bStatut;
    }

}
