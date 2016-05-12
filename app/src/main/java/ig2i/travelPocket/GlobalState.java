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
        loadSuggestionTypes();
        super.onCreate();
    }

    public List<City> cities;
    public Set<String> latlongs;
    public City selectedCity;
    public List<SuggestionType> GoogleSuggestions;
    public List<SuggestionType> UserSuggestions;

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

    public void loadSuggestionTypes() {
        if(prefs.contains("GoogleSuggestions")) {
            Type type = new TypeToken<List<SuggestionType>>(){}.getType();
            GoogleSuggestions = gson.fromJson(prefs.getString("GoogleSuggestions", ""), type);
        } else {
            GoogleSuggestions = new ArrayList<>();
            GoogleSuggestions.add(new SuggestionType("accounting","accounting",false));
            GoogleSuggestions.add(new SuggestionType("airport","airport",false));
            GoogleSuggestions.add(new SuggestionType("amusement_park","amusement_park",false));
            GoogleSuggestions.add(new SuggestionType("aquarium","aquarium",false));
            GoogleSuggestions.add(new SuggestionType("art_gallery","art_gallery",false));
            GoogleSuggestions.add(new SuggestionType("atm","atm",false));
            GoogleSuggestions.add(new SuggestionType("bakery","bakery",false));
            GoogleSuggestions.add(new SuggestionType("bank","bank",false));
            GoogleSuggestions.add(new SuggestionType("bar","bar",false));
            GoogleSuggestions.add(new SuggestionType("beauty_salon","beauty_salon",false));
            GoogleSuggestions.add(new SuggestionType("bicycle_store","bicycle_store",false));
            GoogleSuggestions.add(new SuggestionType("book_store","book_store",false));
            GoogleSuggestions.add(new SuggestionType("bowling_alley","bowling_alley",false));
            GoogleSuggestions.add(new SuggestionType("bus_station","bus_station",false));
            GoogleSuggestions.add(new SuggestionType("cafe","cafe",false));
            GoogleSuggestions.add(new SuggestionType("campground","campground",false));
            GoogleSuggestions.add(new SuggestionType("car_dealer","car_dealer",false));
            GoogleSuggestions.add(new SuggestionType("car_rental","car_rental",false));
            GoogleSuggestions.add(new SuggestionType("car_repair","car_repair",false));
            GoogleSuggestions.add(new SuggestionType("car_wash","car_wash",false));
            GoogleSuggestions.add(new SuggestionType("casino","casino",false));
            GoogleSuggestions.add(new SuggestionType("cemetery","cemetery",false));
            GoogleSuggestions.add(new SuggestionType("church","church",false));
            GoogleSuggestions.add(new SuggestionType("city_hall","city_hall",false));
            GoogleSuggestions.add(new SuggestionType("clothing_store","clothing_store",false));
            GoogleSuggestions.add(new SuggestionType("convenience_store","convenience_store",false));
            GoogleSuggestions.add(new SuggestionType("courthouse","courthouse",false));
            GoogleSuggestions.add(new SuggestionType("dentist","dentist",false));
            GoogleSuggestions.add(new SuggestionType("department_store","department_store",false));
            GoogleSuggestions.add(new SuggestionType("doctor","doctor",false));
            GoogleSuggestions.add(new SuggestionType("electrician","electrician",false));
            GoogleSuggestions.add(new SuggestionType("electronics_store","electronics_store",false));
            GoogleSuggestions.add(new SuggestionType("embassy","embassy",false));
            GoogleSuggestions.add(new SuggestionType("establishment","establishment",false));
            GoogleSuggestions.add(new SuggestionType("finance","finance",false));
            GoogleSuggestions.add(new SuggestionType("fire_station","fire_station",false));
            GoogleSuggestions.add(new SuggestionType("florist","florist",false));
            GoogleSuggestions.add(new SuggestionType("food","food",false));
            GoogleSuggestions.add(new SuggestionType("funeral_home","funeral_home",false));
            GoogleSuggestions.add(new SuggestionType("furniture_store","furniture_store",false));
            GoogleSuggestions.add(new SuggestionType("gas_station","gas_station",false));
            GoogleSuggestions.add(new SuggestionType("general_contractor","general_contractor",false));
            GoogleSuggestions.add(new SuggestionType("grocery_or_supermarket","grocery_or_supermarket",false));
            GoogleSuggestions.add(new SuggestionType("gym","gym",false));
            GoogleSuggestions.add(new SuggestionType("hair_care","hair_care",false));
            GoogleSuggestions.add(new SuggestionType("hardware_store","hardware_store",false));
            GoogleSuggestions.add(new SuggestionType("health","health",false));
            GoogleSuggestions.add(new SuggestionType("hindu_temple","hindu_temple",false));
            GoogleSuggestions.add(new SuggestionType("home_goods_store","home_goods_store",false));
            GoogleSuggestions.add(new SuggestionType("hospital","hospital",false));
            GoogleSuggestions.add(new SuggestionType("insurance_agency","insurance_agency",false));
            GoogleSuggestions.add(new SuggestionType("jewelry_store","jewelry_store",false));
            GoogleSuggestions.add(new SuggestionType("laundry","laundry",false));
            GoogleSuggestions.add(new SuggestionType("lawyer","lawyer",false));
            GoogleSuggestions.add(new SuggestionType("library","library",false));
            GoogleSuggestions.add(new SuggestionType("liquor_store","liquor_store",false));
            GoogleSuggestions.add(new SuggestionType("local_government_office","local_government_office",false));
            GoogleSuggestions.add(new SuggestionType("locksmith","locksmith",false));
            GoogleSuggestions.add(new SuggestionType("lodging","lodging",false));
            GoogleSuggestions.add(new SuggestionType("meal_delivery","meal_delivery",false));
            GoogleSuggestions.add(new SuggestionType("meal_takeaway","meal_takeaway",false));
            GoogleSuggestions.add(new SuggestionType("mosque","mosque",false));
            GoogleSuggestions.add(new SuggestionType("movie_rental","movie_rental",false));
            GoogleSuggestions.add(new SuggestionType("movie_theater","movie_theater",false));
            GoogleSuggestions.add(new SuggestionType("moving_company","moving_company",false));
            GoogleSuggestions.add(new SuggestionType("museum","museum",false));
            GoogleSuggestions.add(new SuggestionType("night_club","night_club",false));
            GoogleSuggestions.add(new SuggestionType("painter","painter",false));
            GoogleSuggestions.add(new SuggestionType("park","park",false));
            GoogleSuggestions.add(new SuggestionType("parking","parking",false));
            GoogleSuggestions.add(new SuggestionType("pet_store","pet_store",false));
            GoogleSuggestions.add(new SuggestionType("pharmacy","pharmacy",false));
            GoogleSuggestions.add(new SuggestionType("physiotherapist","physiotherapist",false));
            GoogleSuggestions.add(new SuggestionType("place_of_worship","place_of_worship",false));
            GoogleSuggestions.add(new SuggestionType("plumber","plumber",false));
            GoogleSuggestions.add(new SuggestionType("police","police",false));
            GoogleSuggestions.add(new SuggestionType("post_office","post_office",false));
            GoogleSuggestions.add(new SuggestionType("real_estate_agency","real_estate_agency",false));
            GoogleSuggestions.add(new SuggestionType("restaurant","restaurant",false));
            GoogleSuggestions.add(new SuggestionType("roofing_contractor","roofing_contractor",false));
            GoogleSuggestions.add(new SuggestionType("rv_park","rv_park",false));
            GoogleSuggestions.add(new SuggestionType("school","school",false));
            GoogleSuggestions.add(new SuggestionType("shoe_store","shoe_store",false));
            GoogleSuggestions.add(new SuggestionType("shopping_mall","shopping_mall",false));
            GoogleSuggestions.add(new SuggestionType("spa","spa",false));
            GoogleSuggestions.add(new SuggestionType("stadium","stadium",false));
            GoogleSuggestions.add(new SuggestionType("storage","storage",false));
            GoogleSuggestions.add(new SuggestionType("store","store",false));
            GoogleSuggestions.add(new SuggestionType("subway_station","subway_station",false));
            GoogleSuggestions.add(new SuggestionType("synagogue","synagogue",false));
            GoogleSuggestions.add(new SuggestionType("taxi_stand","taxi_stand",false));
            GoogleSuggestions.add(new SuggestionType("train_station","train_station",false));
            GoogleSuggestions.add(new SuggestionType("travel_agency","travel_agency",false));
            GoogleSuggestions.add(new SuggestionType("university","university",false));
            GoogleSuggestions.add(new SuggestionType("veterinary_care","veterinary_care",false));
            GoogleSuggestions.add(new SuggestionType("zoo","zoo",false));

            UserSuggestions = new ArrayList<>();
            UserSuggestions.add(new SuggestionType("zoo","zoo",false));
            /*UserSuggestions.add(new SuggestionType("zoo2","zoo2",false));
            UserSuggestions.add(new SuggestionType("zoo2","zoo2",false));
            UserSuggestions.add(new SuggestionType("zoo3","zoo3",false));
            UserSuggestions.add(new SuggestionType("zoo3","zoo3",false));
            UserSuggestions.add(new SuggestionType("zoo3","zoo3",false));
            UserSuggestions.add(new SuggestionType("zoo4","zoo4",false));
            UserSuggestions.add(new SuggestionType("zoo4","zoo4",false));
            UserSuggestions.add(new SuggestionType("zoo4","zoo4",false));
            UserSuggestions.add(new SuggestionType("zoo4","zoo4",false));*/


        }
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
