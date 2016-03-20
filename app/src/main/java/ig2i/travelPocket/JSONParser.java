package ig2i.travelPocket;

/**
 * Created by aBennouna on 13/03/2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    // Classe permettant de parser un JSON
    /*
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    */

    JSONObject JSONresponseText;
    int timeout1 = 1000*2;
    int timeout2 = 1000*2;
    // constructor
    public JSONParser() {

    }

    // Fonction de récupération d'un flux JSON formatté normalement
    public JSONObject getJSONFromUrl(String requesturl) {

        // Making HTTP request
        String result = "";

        Log.d("requesturl = ", requesturl);

        try {
            // defaultHttpClient
            URL url = new URL(requesturl.replace(" ","%20"));
            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout1);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout2);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(20000);
            connection.setRequestProperty("content-Type", "application/json");
            connection.setRequestProperty("content-Language", "en-US");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            reader.close();
            Log.d("SERVICE RESPONSE: ", answer.toString());
            result = answer.toString();

            try {
                JSONresponseText = new JSONObject(result);
                Log.i("JSONresponseText", JSONresponseText.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Log.d("JSON RESPONSE = ", JSONresponseText.toString());
        return JSONresponseText;

    }


    // Fonction de récupération du flux JSON de flickr
    // Il ne'est pas reconnu comme un flux JSON
    // il faudra faire des modifications avant de le parser
    public JSONObject getJSONFromUrlFlickr(String requesturl) {

        // Making HTTP request
        String result = "";

        Log.d("requesturl = ", requesturl);

        try {
            // defaultHttpClient
            URL url = new URL(requesturl.replace(" ","%20"));
            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout1);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout2);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(20000);
            connection.setRequestProperty("content-Type", "application/json");
            connection.setRequestProperty("content-Language", "en-US");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            reader.close();
            Log.d("SERVICE RESPONSE: ", answer.toString());
            result = answer.toString();
            result = result.replace("jsonFlickrApi(", "");
            result = result.replace("\"stat\":\"ok\"})","\"stat\":\"ok\"}");

            try {
                JSONresponseText = new JSONObject(result);
                Log.i("JSONresponseText", JSONresponseText.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Log.d("JSON RESPONSE = ", JSONresponseText.toString());
        return JSONresponseText;

    }
}
