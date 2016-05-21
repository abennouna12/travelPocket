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
        suggests.add(new SuggestionType("Finance","accounting",false));
        suggests.add(new SuggestionType("Aéroport","airport",false));
        suggests.add(new SuggestionType("Parc d'attraction","amusement_park",false));
        suggests.add(new SuggestionType("Aquarium","aquarium",false));
        suggests.add(new SuggestionType("Galerie d'art","art_gallery",false));
        suggests.add(new SuggestionType("Guichet automatique","atm",false));
        suggests.add(new SuggestionType("Boulangerie","bakery",false));
        suggests.add(new SuggestionType("Banque","bank",false));
        suggests.add(new SuggestionType("Bar","bar",false));
        suggests.add(new SuggestionType("Salon de beauté","beauty_salon",false));
        suggests.add(new SuggestionType("Magasin de vélo","bicycle_store",false));
        suggests.add(new SuggestionType("Librairie","book_store",false));
        suggests.add(new SuggestionType("Bowling","bowling_alley",false));
        suggests.add(new SuggestionType("Station de bus","bus_station",false));
        suggests.add(new SuggestionType("Café","cafe",false));
        suggests.add(new SuggestionType("Camping","campground",false));
        suggests.add(new SuggestionType("Concessionnaire","car_dealer",false));
        suggests.add(new SuggestionType("Location de voiture","car_rental",false));
        suggests.add(new SuggestionType("Réparation automobile","car_repair",false));
        suggests.add(new SuggestionType("Lave-auto","car_wash",false));
        suggests.add(new SuggestionType("Casino","casino",false));
        suggests.add(new SuggestionType("Cimetière","cemetery",false));
        suggests.add(new SuggestionType("Eglise","church",false));
        suggests.add(new SuggestionType("Mairie","city_hall",false));
        suggests.add(new SuggestionType("Magasin de vêtements","clothing_store",false));
        suggests.add(new SuggestionType("Tribunal","courthouse",false));
        suggests.add(new SuggestionType("Dentiste","dentist",false));
        suggests.add(new SuggestionType("Médecin","doctor",false));
        suggests.add(new SuggestionType("Electricien","electrician",false));
        suggests.add(new SuggestionType("Magasin d'électronique","electronics_store",false));
        suggests.add(new SuggestionType("Embassade","embassy",false));
        suggests.add(new SuggestionType("Finance","finance",false));
        suggests.add(new SuggestionType("Caserne de pompiers","fire_station",false));
        suggests.add(new SuggestionType("Fleuriste","florist",false));
        suggests.add(new SuggestionType("Maison funéraire","funeral_home",false));
        suggests.add(new SuggestionType("Magasin de meubles","furniture_store",false));
        suggests.add(new SuggestionType("Station-essence","gas_station",false));
        suggests.add(new SuggestionType("Entrepreneur général","general_contractor",false));
        suggests.add(new SuggestionType("Epicerie ou Supermarché","grocery_or_supermarket",false));
        suggests.add(new SuggestionType("Gym","gym",false));
        suggests.add(new SuggestionType("Soin des cheveux","hair_care",false));
        suggests.add(new SuggestionType("Quincaillerie","hardware_store",false));
        suggests.add(new SuggestionType("Santé","health",false));
        suggests.add(new SuggestionType("Temple indien","hindu_temple",false));
        suggests.add(new SuggestionType("Hôpital","hospital",false));
        suggests.add(new SuggestionType("Agence d'assurance","insurance_agency",false));
        suggests.add(new SuggestionType("Bijouterie","jewelry_store",false));
        suggests.add(new SuggestionType("Blanchisserie","laundry",false));
        suggests.add(new SuggestionType("Avocat","lawyer",false));
        suggests.add(new SuggestionType("Bibliothèque","library",false));
        suggests.add(new SuggestionType("Magasin d'alcool","liquor_store",false));
        suggests.add(new SuggestionType("Bureau local du gouvernement","local_government_office",false));
        suggests.add(new SuggestionType("Serrurier","locksmith",false));
        suggests.add(new SuggestionType("Hébergement","lodging",false));
        suggests.add(new SuggestionType("Livraison de repas","meal_delivery",false));
        suggests.add(new SuggestionType("Repas à emporter","meal_takeaway",false));
        suggests.add(new SuggestionType("Mosquée","mosque",false));
        suggests.add(new SuggestionType("Location de films","movie_rental",false));
        suggests.add(new SuggestionType("Cinéma","movie_theater",false));
        suggests.add(new SuggestionType("Entreprise de déménagement","moving_company",false));
        suggests.add(new SuggestionType("Musée","museum",false));
        suggests.add(new SuggestionType("Boîte de nuit","night_club",false));
        suggests.add(new SuggestionType("Peintre","painter",false));
        suggests.add(new SuggestionType("Parc","park",false));
        suggests.add(new SuggestionType("Parking","parking",false));
        suggests.add(new SuggestionType("Animalerie","pet_store",false));
        suggests.add(new SuggestionType("Pharmacie","pharmacy",false));
        suggests.add(new SuggestionType("Physiothérapeute","physiotherapist",false));
        suggests.add(new SuggestionType("Lieu de culte","place_of_worship",false));
        suggests.add(new SuggestionType("Plombier","plumber",false));
        suggests.add(new SuggestionType("Police","police",false));
        suggests.add(new SuggestionType("Bureau de poste","post_office",false));
        suggests.add(new SuggestionType("Agence immobilière","real_estate_agency",false));
        suggests.add(new SuggestionType("Restaurant","restaurant",false));
        suggests.add(new SuggestionType("Couvreur","roofing_contractor",false));
        suggests.add(new SuggestionType("Ecole","school",false));
        suggests.add(new SuggestionType("Magasin de chaussures","shoe_store",false));
        suggests.add(new SuggestionType("Centre commercial","shopping_mall",false));
        suggests.add(new SuggestionType("Spa","spa",false));
        suggests.add(new SuggestionType("Stade","stadium",false));
        suggests.add(new SuggestionType("Stockage","storage",false));
        suggests.add(new SuggestionType("Magasin","store",false));
        suggests.add(new SuggestionType("Station de métro","subway_station",false));
        suggests.add(new SuggestionType("Synagogue","synagogue",false));
        suggests.add(new SuggestionType("Station de taxi","taxi_stand",false));
        suggests.add(new SuggestionType("Gare de train","train_station",false));
        suggests.add(new SuggestionType("Agence de voyage","travel_agency",false));
        suggests.add(new SuggestionType("Université","university",false));
        suggests.add(new SuggestionType("Soins vétérinaires","veterinary_care",false));
        suggests.add(new SuggestionType("Zoo","zoo",false));
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

    public List<SuggestionType> getTypes() {
        List<SuggestionType> types = new ArrayList<>();
        for (SuggestionType s:GoogleSuggestions) {
            if(s.checked) {
                if (!types.contains(s)) {
                    types.add(s);
                }
            }
        }
        for (SuggestionType s:UserSuggestions) {
            if(s.checked) {
                if (!types.contains(s)) {
                    types.add(s);
                }
            }
        }
        return types;
    }

    public String getTypesStringNames (){
        String output = "";
        for(SuggestionType s:getTypes()) {
            output = output + "," + s.getName();
        }
        return (output.equals("")) ? "" : output.substring(1);
    }

    public String getTypesStringValues (){
        String output = "";
        for(SuggestionType s:getTypes()) {
            output = output + "|" + s.getValue();
        }
        return (output.equals("")) ? "all" : output.substring(1);
    }

    public String getFilterNames() {
        String [] names = getTypesStringNames().split(",");
        if(getTypesStringNames().equals("")) {
            return "Tout les filtres";
        }
        switch (names.length) {
            case 0:
                return "Tout les filtres";
            case 1:
                return names[0];
            case 2:
                return names[0] + ", " + names[1];
            case 3:
                return names[0] + ", " + names[1] + ", " + names[2];
            default:
                return names[0] + ", " + names[1] + ", " + names[2] + ", ...";

        }
    }

}
