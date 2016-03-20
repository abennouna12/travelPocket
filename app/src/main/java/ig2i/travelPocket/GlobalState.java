package ig2i.travelPocket;

        import android.app.Application;
        import android.util.Log;
        import android.widget.Toast;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.List;


/**
 * Created by aBennouna on 06/03/2016.
 */
public class GlobalState extends Application{
    static String cat = "MyTravelPocket";

    @Override
    public void onCreate() {
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
    List<String> latlongs;
    City selectedCity;

    // Fonction lancée au démarrage de l'application pour initialiser les villes
    // (A modifier pour enregistrer la liste dans les system parameters)
    public void loadCities() {
        if (cities==null) {
            cities = new ArrayList<>();
        }
        if (latlongs==null) {
            latlongs = new ArrayList<>();
        }
    }

    // Fonction random permet de retourner un int entre deux valeurs prédéfinis
    // Cette fonction est principalement utilisée lors de la récupération des images
    // de l'API Flickr, on choisis une image random
    public int random(int min, int max) {
        return min + (int)(Math.random() * max);
    }

    public String getWeatherDescription (int weatherID)
    {
        switch (weatherID){
            default :
                return "";
            case 200 :
                return "Orage avec faibles précipitations";
            case 201 :
                return "Orage avec pluie";
            case 202 :
                return "Orage avec fortes précipitations";
            case 210 :
                return "Orage faible";
            case 211 :
                return "Orage";
            case 212 :
                return "Orage violent";
            //à vérifier
            case 221 :
                return "Orage loqueteux";
            case 230 :
                return "Orage avec bruine légère";
            case 231 :
                return "Orage avec bruine";
            case 232 :
                return "Orage avec bruine verglacante";
        }
    }

}
