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
        import java.util.Map;
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
        //deleteSuggestions();
    }

    public List<City> cities;
    public Set<String> latlongs;
    public City selectedCity;
    public List<FilterType> GoogleSuggestions;
    public List<FilterType> UserSuggestions;
    public List<FilterType> virginGoogleSuggestions;

    /**
     * Fonction pour afficher un toast sur le telephone et un log dans la console pour alerter
     * @param s String a afficher
     */
    public void alerter(String s) {
        // Afficher un toast dans le telephone
        toast(s);
        // Afficher dans la console
        Log.i(tag, s);
    }

    /**
     * Fonction pour afficher un toast
     * @param s Toast a afficher
     */
    public void toast(String s) {
        Toast t = Toast.makeText(this, s, Toast.LENGTH_LONG);
        t.show();
    }

    /**
     * Fonction permettant de charger les filtres
     */
    public void loadSuggestionTypes() {
        virginGoogleSuggestions = getVirginSuggestions();
        if(prefs.contains("GoogleSuggestions")) {
            Type type = new TypeToken<List<FilterType>>(){}.getType();
            GoogleSuggestions = gson.fromJson(prefs.getString("GoogleSuggestions", ""), type);
        } else {
            GoogleSuggestions = virginGoogleSuggestions;
        }

        if(prefs.contains("UserSuggestions")) {
            Type type = new TypeToken<List<FilterType>>(){}.getType();
            UserSuggestions = gson.fromJson(prefs.getString("UserSuggestions", ""), type);
        } else {
            UserSuggestions = new ArrayList<>();
            //UserSuggestions.add(new FilterType("zoo","zoo",false));
        }
    }

    /**
     * Fonction permettant d'initialiser les filtres
     * @return Filtres Google
     */
    public List<FilterType> getVirginSuggestions() {

        List<FilterType> suggests = new ArrayList<>();
        suggests.add(new FilterType("Aéroport","airport",false));
        suggests.add(new FilterType("Agence d'assurance","insurance_agency",false));
        suggests.add(new FilterType("Agence de voyage","travel_agency",false));
        suggests.add(new FilterType("Agence immobilière","real_estate_agency",false));
        suggests.add(new FilterType("Ambassade","embassy",false));
        suggests.add(new FilterType("Animalerie","pet_store",false));
        suggests.add(new FilterType("Aquarium","aquarium",false));
        suggests.add(new FilterType("Avocat","lawyer",false));
        suggests.add(new FilterType("Banque","bank",false));
        suggests.add(new FilterType("Bar","bar",false));
        suggests.add(new FilterType("Bibliothèque","library",false));
        suggests.add(new FilterType("Bijouterie","jewelry_store",false));
        suggests.add(new FilterType("Blanchisserie","laundry",false));
        suggests.add(new FilterType("Boîte de nuit","night_club",false));
        suggests.add(new FilterType("Boulangerie","bakery",false));
        suggests.add(new FilterType("Boutique","store",false));
        suggests.add(new FilterType("Bowling","bowling_alley",false));
        suggests.add(new FilterType("Bureau de poste","post_office",false));
        suggests.add(new FilterType("Bureau local du gouvernement","local_government_office",false));
        suggests.add(new FilterType("Café","cafe",false));
        suggests.add(new FilterType("Camping","campground",false));
        suggests.add(new FilterType("Caserne de pompiers","fire_station",false));
        suggests.add(new FilterType("Casino","casino",false));
        suggests.add(new FilterType("Centre commercial","shopping_mall",false));
        suggests.add(new FilterType("Cimetière","cemetery",false));
        suggests.add(new FilterType("Cinéma","movie_theater",false));
        suggests.add(new FilterType("Concessionnaire","car_dealer",false));
        suggests.add(new FilterType("Couvreur","roofing_contractor",false));
        suggests.add(new FilterType("Dentiste","dentist",false));
        suggests.add(new FilterType("Ecole","school",false));
        suggests.add(new FilterType("Eglise","church",false));
        suggests.add(new FilterType("Electricien","electrician",false));
        suggests.add(new FilterType("Entrepreneur général","general_contractor",false));
        suggests.add(new FilterType("Entreprise de déménagement","moving_company",false));
        suggests.add(new FilterType("Epicerie ou Supermarché","grocery_or_supermarket",false));
        suggests.add(new FilterType("Finance","finance",false));
        suggests.add(new FilterType("Fleuriste","florist",false));
        suggests.add(new FilterType("Galerie d'art","art_gallery",false));
        suggests.add(new FilterType("Gare de train","train_station",false));
        suggests.add(new FilterType("Guichet automatique","atm",false));
        suggests.add(new FilterType("Gym","gym",false));
        suggests.add(new FilterType("Hébergement","lodging",false));
        suggests.add(new FilterType("Hôpital","hospital",false));
        suggests.add(new FilterType("Lave-auto","car_wash",false));
        suggests.add(new FilterType("Librairie","book_store",false));
        suggests.add(new FilterType("Lieu de culte","place_of_worship",false));
        suggests.add(new FilterType("Livraison de repas","meal_delivery",false));
        suggests.add(new FilterType("Location de films","movie_rental",false));
        suggests.add(new FilterType("Location de voiture","car_rental",false));
        suggests.add(new FilterType("Magasin d'alcool","liquor_store",false));
        suggests.add(new FilterType("Magasin d'électronique","electronics_store",false));
        suggests.add(new FilterType("Magasin de chaussures","shoe_store",false));
        suggests.add(new FilterType("Magasin de meubles","furniture_store",false));
        suggests.add(new FilterType("Magasin de vélo","bicycle_store",false));
        suggests.add(new FilterType("Magasin de vêtements","clothing_store",false));
        suggests.add(new FilterType("Mairie","city_hall",false));
        suggests.add(new FilterType("Maison funéraire","funeral_home",false));
        suggests.add(new FilterType("Médecin","doctor",false));
        suggests.add(new FilterType("Mosquée","mosque",false));
        suggests.add(new FilterType("Musée","museum",false));
        suggests.add(new FilterType("Parc","park",false));
        suggests.add(new FilterType("Parc d'attraction","amusement_park",false));
        suggests.add(new FilterType("Parking","parking",false));
        suggests.add(new FilterType("Peintre","painter",false));
        suggests.add(new FilterType("Pharmacie","pharmacy",false));
        suggests.add(new FilterType("Physiothérapeute","physiotherapist",false));
        suggests.add(new FilterType("Plombier","plumber",false));
        suggests.add(new FilterType("Police","police",false));
        suggests.add(new FilterType("Quincaillerie","hardware_store",false));
        suggests.add(new FilterType("Réparation automobile","car_repair",false));
        suggests.add(new FilterType("Repas à emporter","meal_takeaway",false));
        suggests.add(new FilterType("Restaurant","restaurant",false));
        suggests.add(new FilterType("Salon de beauté","beauty_salon",false));
        suggests.add(new FilterType("Santé","health",false));
        suggests.add(new FilterType("Serrurier","locksmith",false));
        suggests.add(new FilterType("Soins des cheveux","hair_care",false));
        suggests.add(new FilterType("Soins vétérinaires","veterinary_care",false));
        suggests.add(new FilterType("Spa","spa",false));
        suggests.add(new FilterType("Stade","stadium",false));
        suggests.add(new FilterType("Station de métro","subway_station",false));
        suggests.add(new FilterType("Station de bus","bus_station",false));
        suggests.add(new FilterType("Station de taxi","taxi_stand",false));
        suggests.add(new FilterType("Station-essence","gas_station",false));
        suggests.add(new FilterType("Stockage","storage",false));
        suggests.add(new FilterType("Synagogue","synagogue",false));
        suggests.add(new FilterType("Temple indien","hindu_temple",false));
        suggests.add(new FilterType("Tribunal","courthouse",false));
        suggests.add(new FilterType("Université","university",false));
        suggests.add(new FilterType("Zoo","zoo",false));
        return suggests;

    }

    /**
     * Fonction lancée au démarrage de l'application pour initialiser les villes
     */
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

    /**
     * Fonction random permet de retourner un int entre deux valeurs prédéfinis
     * Cette fonction est principalement utilisée lors de la récupération des images
     * de l'API Flickr et des suggestions de Google Places, on choisis une image random
     * @param min Min
     * @param max Max
     * @return Random value
     */
    public int random(int min, int max) {
        return min + (int)(Math.random() * max);
    }

    /**
     * Fonction permettant de traduire les jours obtenus par l'API Forecast.io (Anglais->Francais)
     * @param engDay Jour en Anglais
     * @return Jour en Francais
     */
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

    /**
     * Fonction permettant de recuperer la valeur des parametres concernant la mise a jour
     * des photos
     * @return True si l'utilisateur souhaite modifier les photos
     */
    public Boolean isPhotosUpdatable() {
        return prefs.getBoolean("updatePhoto",false);
    }

    /**
     * Fonction permettant de verifier si l'utilisateur est connecté ou non
     * @return True si l'utilisateur connecté
     */
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

    /**
     * Fonction permettant d'avoir les valeurs d'une liste de filtres
     * @param suggests List de filtres
     * @return Valeurs splittés en |
     */
    public String getTypeValues(List<FilterType> suggests) {
        String output = "";
        for(FilterType s:suggests) {
            output = output + "|" + s.getValue();
        }
        return (output.equals("")) ? "" : output.substring(1);
    }

    /**
     * Fonction permettant de mettre a jour les filtres dans les shared preferences
     */
    public void updatePrefsSuggestions() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("UserSuggestions", gson.toJson(UserSuggestions));
        editor.putString("GoogleSuggestions", gson.toJson(GoogleSuggestions));
        editor.apply();
    }

    /**
     * Fonction permettant d'avoir les filtres selectionnés
     * @return List des filtres
     */
    public List<FilterType> getSelectedFilters() {
        List<FilterType> types = new ArrayList<>();
        for (FilterType sg:GoogleSuggestions) {
            if(sg.checked) {
                types.add(sg);
            }
        }
        for (FilterType su:UserSuggestions) {
            if(su.checked) {
                types.add(su);
            }
        }
        return types;
    }

    /**
     * Fonction permettant d'avoir les valeurs des filtres selectionnés splittés pour l'API
     * @return Valeurs de filtres selectionnés splittés pour l'API
     */
    public String getTypesStringNames (){
        String output = "";
        for(FilterType s:getSelectedFilters()) {
            output = output + "," + s.getName();
        }
        return (output.equals("")) ? "" : output.substring(1);
    }

    /**
     * Fonction permettant d'avoir les valeurs des filtres selectionnés
     * @return Valeurs de filtres selectionnés
     */
    public String getTypesStringValues (){
        String output = "";
        for(FilterType s:getSelectedFilters()) {
            output = output + "|" + s.getValue();
        }
        return (output.equals("")) ? "all" : output.substring(1);
    }

    /**
     * Fonction permettant d'avoir les noms des filtres selectionnés
     * @return Noms de filtres selectionnés
     */
    public String getFilterNames() {
        String [] names = getTypesStringNames().split(",");
        if(getTypesStringNames().equals("")) {
            return "Tous les filtres";
        }

        switch (names.length) {
            case 0:
                return "Tous les filtres";
            case 1:
                return names[0];
            case 2:
                return names[0] + ", " + names[1];
            case 3:
                return names[0] + ", " + names[1] + ", " + names[2];
            default:
                return names[0] + ", " + names[1] + ", " + names[2] + " et " + (names.length-3) + " filtres";

        }
    }

    /**
     * Fonction permettant de supprimer les suggestions des shared preferences
     */
    public void deleteSuggestions() {
        Map<String,?> keys = prefs.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getKey().contains("Suggestion:")) {
                prefs.edit().remove(entry.getKey()).commit();
            }
        }
    }

    /**
     * Fonction permettant de verifier si tous les filtres on été choisis ou non
     * @return True si tout les filtres sont selectionnés
     */
    public Boolean isAllSuggestionsChecked() {
        int totalSuggests = GoogleSuggestions.size();
        int sTrue = 0;
        for(FilterType s:GoogleSuggestions) {
            if(s.checked) {
                sTrue ++;
            }
        }
        return (totalSuggests == sTrue) ? true : false;

    }

}
