package ig2i.travelPocket.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class JSONParser {

    // Classe permettant de parser un JSON

    JSONObject JSONresponseText;
    int timeout1 = 1000*2;
    int timeout2 = 1000*2;
    // constructor
    public JSONParser() {

    }

    // Fonction de récupération d'un flux JSON formatté normalement
    public JSONObject getJSONFromUrl(String requesturl) {

        // Making HTTP request

        Log.d("requesturl = ", requesturl);

        try {
            // defaultHttpClient
            URL url = new URL(requesturl);
            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, timeout1);
            HttpConnectionParams.setSoTimeout(httpParameters, timeout2);
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(20000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept","*/*");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            StringBuilder answer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line).append("\r\n");
            }
            reader.close();
            Log.d("SERVICE RESPONSE: ", answer.toString());

            try {
                JSONresponseText = new JSONObject(answer.toString());
                Log.i("JSONresponseText", JSONresponseText.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

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
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept","*/*");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            StringBuilder answer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            reader.close();
            Log.d("SERVICE RESPONSE: ", answer.toString());

            try {
                JSONresponseText = new JSONObject(answer.toString()
                        .replace("jsonFlickrApi(", "")
                        .replace("\"stat\":\"ok\"})","\"stat\":\"ok\"}"));
                Log.i("JSONresponseText", JSONresponseText.toString());
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Log.d("JSON RESPONSE = ", JSONresponseText.toString());
        return JSONresponseText;

    }
}
