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
    public List<SuggestionType> virginGoogleSuggestions;

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
        virginGoogleSuggestions = getVirginSuggestions();
        if(prefs.contains("GoogleSuggestions")) {
            Type type = new TypeToken<List<SuggestionType>>(){}.getType();
            GoogleSuggestions = gson.fromJson(prefs.getString("GoogleSuggestions", ""), type);
        } else {
            GoogleSuggestions = virginGoogleSuggestions;
        }

        if(prefs.contains("UserSuggestions")) {
            Type type = new TypeToken<List<SuggestionType>>(){}.getType();
            UserSuggestions = gson.fromJson(prefs.getString("UserSuggestions", ""), type);
        } else {
            UserSuggestions = new ArrayList<>();
            //UserSuggestions.add(new SuggestionType("zoo","zoo",false));
        }
    }

    public List<SuggestionType> getVirginSuggestions() {

        List<SuggestionType> suggests = new ArrayList<>();
        suggests.add(new SuggestionType("accounting","accounting",false));
        suggests.add(new SuggestionType("airport","airport",false));
        suggests.add(new SuggestionType("amusement_park","amusement_park",false));
        suggests.add(new SuggestionType("aquarium","aquarium",false));
        suggests.add(new SuggestionType("art_gallery","art_gallery",false));
        suggests.add(new SuggestionType("atm","atm",false));
        suggests.add(new SuggestionType("bakery","bakery",false));
        suggests.add(new SuggestionType("bank","bank",false));
        suggests.add(new SuggestionType("bar","bar",false));
        suggests.add(new SuggestionType("beauty_salon","beauty_salon",false));
        suggests.add(new SuggestionType("bicycle_store","bicycle_store",false));
        suggests.add(new SuggestionType("book_store","book_store",false));
        suggests.add(new SuggestionType("bowling_alley","bowling_alley",false));
        suggests.add(new SuggestionType("bus_station","bus_station",false));
        suggests.add(new SuggestionType("cafe","cafe",false));
        suggests.add(new SuggestionType("campground","campground",false));
        suggests.add(new SuggestionType("car_dealer","car_dealer",false));
        suggests.add(new SuggestionType("car_rental","car_rental",false));
        suggests.add(new SuggestionType("car_repair","car_repair",false));
        suggests.add(new SuggestionType("car_wash","car_wash",false));
        suggests.add(new SuggestionType("casino","casino",false));
        suggests.add(new SuggestionType("cemetery","cemetery",false));
        suggests.add(new SuggestionType("church","church",false));
        suggests.add(new SuggestionType("city_hall","city_hall",false));
        suggests.add(new SuggestionType("clothing_store","clothing_store",false));
        suggests.add(new SuggestionType("convenience_store","convenience_store",false));
        suggests.add(new SuggestionType("courthouse","courthouse",false));
        suggests.add(new SuggestionType("dentist","dentist",false));
        suggests.add(new SuggestionType("department_store","department_store",false));
        suggests.add(new SuggestionType("doctor","doctor",false));
        suggests.add(new SuggestionType("electrician","electrician",false));
        suggests.add(new SuggestionType("electronics_store","electronics_store",false));
        suggests.add(new SuggestionType("embassy","embassy",false));
        suggests.add(new SuggestionType("establishment","establishment",false));
        suggests.add(new SuggestionType("finance","finance",false));
        suggests.add(new SuggestionType("fire_station","fire_station",false));
        suggests.add(new SuggestionType("florist","florist",false));
        suggests.add(new SuggestionType("food","food",false));
        suggests.add(new SuggestionType("funeral_home","funeral_home",false));
        suggests.add(new SuggestionType("furniture_store","furniture_store",false));
        suggests.add(new SuggestionType("gas_station","gas_station",false));
        suggests.add(new SuggestionType("general_contractor","general_contractor",false));
        suggests.add(new SuggestionType("grocery_or_supermarket","grocery_or_supermarket",false));
        suggests.add(new SuggestionType("gym","gym",false));
        suggests.add(new SuggestionType("hair_care","hair_care",false));
        suggests.add(new SuggestionType("hardware_store","hardware_store",false));
        suggests.add(new SuggestionType("health","health",false));
        suggests.add(new SuggestionType("hindu_temple","hindu_temple",false));
        suggests.add(new SuggestionType("home_goods_store","home_goods_store",false));
        suggests.add(new SuggestionType("hospital","hospital",false));
        suggests.add(new SuggestionType("insurance_agency","insurance_agency",false));
        suggests.add(new SuggestionType("jewelry_store","jewelry_store",false));
        suggests.add(new SuggestionType("laundry","laundry",false));
        suggests.add(new SuggestionType("lawyer","lawyer",false));
        suggests.add(new SuggestionType("library","library",false));
        suggests.add(new SuggestionType("liquor_store","liquor_store",false));
        suggests.add(new SuggestionType("local_government_office","local_government_office",false));
        suggests.add(new SuggestionType("locksmith","locksmith",false));
        suggests.add(new SuggestionType("lodging","lodging",false));
        suggests.add(new SuggestionType("meal_delivery","meal_delivery",false));
        suggests.add(new SuggestionType("meal_takeaway","meal_takeaway",false));
        suggests.add(new SuggestionType("mosque","mosque",false));
        suggests.add(new SuggestionType("movie_rental","movie_rental",false));
        suggests.add(new SuggestionType("movie_theater","movie_theater",false));
        suggests.add(new SuggestionType("moving_company","moving_company",false));
        suggests.add(new SuggestionType("museum","museum",false));
        suggests.add(new SuggestionType("night_club","night_club",false));
        suggests.add(new SuggestionType("painter","painter",false));
        suggests.add(new SuggestionType("park","park",false));
        suggests.add(new SuggestionType("parking","parking",false));
        suggests.add(new SuggestionType("pet_store","pet_store",false));
        suggests.add(new SuggestionType("pharmacy","pharmacy",false));
        suggests.add(new SuggestionType("physiotherapist","physiotherapist",false));
        suggests.add(new SuggestionType("place_of_worship","place_of_worship",false));
        suggests.add(new SuggestionType("plumber","plumber",false));
        suggests.add(new SuggestionType("police","police",false));
        suggests.add(new SuggestionType("post_office","post_office",false));
        suggests.add(new SuggestionType("real_estate_agency","real_estate_agency",false));
        suggests.add(new SuggestionType("restaurant","restaurant",false));
        suggests.add(new SuggestionType("roofing_contractor","roofing_contractor",false));
        suggests.add(new SuggestionType("rv_park","rv_park",false));
        suggests.add(new SuggestionType("school","school",false));
        suggests.add(new SuggestionType("shoe_store","shoe_store",false));
        suggests.add(new SuggestionType("shopping_mall","shopping_mall",false));
        suggests.add(new SuggestionType("spa","spa",false));
        suggests.add(new SuggestionType("stadium","stadium",false));
        suggests.add(new SuggestionType("storage","storage",false));
        suggests.add(new SuggestionType("store","store",false));
        suggests.add(new SuggestionType("subway_station","subway_station",false));
        suggests.add(new SuggestionType("synagogue","synagogue",false));
        suggests.add(new SuggestionType("taxi_stand","taxi_stand",false));
        suggests.add(new SuggestionType("train_station","train_station",false));
        suggests.add(new SuggestionType("travel_agency","travel_agency",false));
        suggests.add(new SuggestionType("university","university",false));
        suggests.add(new SuggestionType("veterinary_care","veterinary_care",false));
        suggests.add(new SuggestionType("zoo","zoo",false));
        return suggests;
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

    public String getTypeValues(List<SuggestionType> suggests) {
        String output = "";
        for(SuggestionType s:suggests) {
            output = output + "|" + s.getValue();
        }
        return (output.equals("")) ? "" : output.substring(1);
    }

    public void updatePrefsSuggestions() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserSuggestions", gson.toJson(UserSuggestions));
        editor.putString("GoogleSuggestions", gson.toJson(GoogleSuggestions));
        editor.apply();
    }

}
