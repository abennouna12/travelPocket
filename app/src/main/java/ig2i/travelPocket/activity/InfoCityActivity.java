package ig2i.travelPocket.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.adapter.RVASuggestion;
import ig2i.travelPocket.model.City;
import ig2i.travelPocket.service.JSONSuggestions;

public class InfoCityActivity extends Activity implements View.OnClickListener {

    public GlobalState gs;

    TextView temps;
    TextView ville;
    TextView currentWeather;
    SimpleDraweeView picture;

    TextView dayToday;
    TextView tempMinToday;
    TextView tempMaxToday;
    SimpleDraweeView picToday;

    TextView dayDay1;
    TextView tempMinDay1;
    TextView tempMaxDay1;
    SimpleDraweeView picDay1;

    TextView dayDay2;
    TextView tempMinDay2;
    TextView tempMaxDay2;
    SimpleDraweeView picDay2;

    TextView dayDay3;
    TextView tempMinDay3;
    TextView tempMaxDay3;
    SimpleDraweeView picDay3;

    TextView dayDay4;
    TextView tempMinDay4;
    TextView tempMaxDay4;
    SimpleDraweeView picDay4;

    RVASuggestion adapter;
    LinearLayoutManager llm;
    RecyclerView rvSuggestions;

    LocationManager locationManager = null;
    LocationListener locationListener = null;

    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_city);

        gs = (GlobalState) getApplication();
        Fresco.initialize(this);

        gson = new Gson();

        adapter = new RVASuggestion(gs);

        setView();

        setSuggestions();

        setLocation();

    }

    // Fonction permettant de recuperer les suggestions
    public void setSuggestions() {
        getJSONSuggestions();
    }

    // Fonction permettant de recuperer les suggestions a l'aide de la classe JSONParser
    public void getJSONSuggestions() {
        if (gs.isConnected()) {
            JSONSuggestions w = new JSONSuggestions(this);
            w.execute();
        } else {
            gs.alerter("Veuillez verifier votre connexion");
        }
    }

    // Cette fonction permet d'initialiset et de donner une valeur a tout les textviews et images
    // de la vue
    public void setView() {

        temps = (TextView) findViewById(R.id.temps);
        ville = (TextView) findViewById(R.id.ville);
        currentWeather = (TextView) findViewById(R.id.currentWeather);
        picture = (SimpleDraweeView) findViewById(R.id.picture);

        dayToday = (TextView) findViewById(R.id.dayToday);
        tempMinToday = (TextView) findViewById(R.id.tempMinToday);
        tempMaxToday = (TextView) findViewById(R.id.tempMaxToday);
        picToday = (SimpleDraweeView) findViewById(R.id.picToday);

        dayDay1 = (TextView) findViewById(R.id.dayDay1);
        tempMinDay1 = (TextView) findViewById(R.id.tempMinDay1);
        tempMaxDay1 = (TextView) findViewById(R.id.tempMaxDay1);
        picDay1 = (SimpleDraweeView) findViewById(R.id.picDay1);

        dayDay2 = (TextView) findViewById(R.id.dayDay2);
        tempMinDay2 = (TextView) findViewById(R.id.tempMinDay2);
        tempMaxDay2 = (TextView) findViewById(R.id.tempMaxDay2);
        picDay2 = (SimpleDraweeView) findViewById(R.id.picDay2);

        dayDay3 = (TextView) findViewById(R.id.dayDay3);
        tempMinDay3 = (TextView) findViewById(R.id.tempMinDay3);
        tempMaxDay3 = (TextView) findViewById(R.id.tempMaxDay3);
        picDay3 = (SimpleDraweeView) findViewById(R.id.picDay3);

        dayDay4 = (TextView) findViewById(R.id.dayDay4);
        tempMinDay4 = (TextView) findViewById(R.id.tempMinDay4);
        tempMaxDay4 = (TextView) findViewById(R.id.tempMaxDay4);
        picDay4 = (SimpleDraweeView) findViewById(R.id.picDay4);

        ville.setText(gs.selectedCity.name);
        temps.setText(gs.selectedCity.description);
        currentWeather.setText(gs.selectedCity.currentWeather);
        Uri uri = Uri.parse(gs.selectedCity.picture);
        picture.setImageURI(uri);

        dayToday.setText(gs.selectedCity.daily.get(0).date);
        dayDay1.setText(gs.selectedCity.daily.get(1).date);
        dayDay2.setText(gs.selectedCity.daily.get(2).date);
        dayDay3.setText(gs.selectedCity.daily.get(3).date);
        dayDay4.setText(gs.selectedCity.daily.get(4).date);

        tempMinToday.setText(gs.selectedCity.daily.get(0).tempMin);
        tempMinDay1.setText(gs.selectedCity.daily.get(1).tempMin);
        tempMinDay2.setText(gs.selectedCity.daily.get(2).tempMin);
        tempMinDay3.setText(gs.selectedCity.daily.get(3).tempMin);
        tempMinDay4.setText(gs.selectedCity.daily.get(4).tempMin);

        tempMaxToday.setText(gs.selectedCity.daily.get(0).tempMax);
        tempMaxDay1.setText(gs.selectedCity.daily.get(1).tempMax);
        tempMaxDay2.setText(gs.selectedCity.daily.get(2).tempMax);
        tempMaxDay3.setText(gs.selectedCity.daily.get(3).tempMax);
        tempMaxDay4.setText(gs.selectedCity.daily.get(4).tempMax);

        picToday.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(0).icon, null, getPackageName())));
        picDay1.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(1).icon, null, getPackageName())));
        picDay2.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(2).icon, null, getPackageName())));
        picDay3.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(3).icon, null, getPackageName())));
        picDay4.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(4).icon, null, getPackageName())));
    }

    // Fonction permettant d'initialiser la geolocalisation
    public void setLocation() {


        locationListener = new MyLocationListener();

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
                return;
        }
        locationManager.requestLocationUpdates(LocationManager
                .GPS_PROVIDER, 5000, 100, locationListener);


    }

    // Classe de LocationListener permettant de mettre a jour la position actuelle et l'enregistrer
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(final Location location) {
            SharedPreferences.Editor editor = gs.prefs.edit();
            editor.putString("currentLat", Double.toString(location.getLatitude()));
            editor.putString("currentLng", Double.toString(location.getLongitude()));
            editor.apply();
            // Apres avoir enregistrer la nouvelle position actuelle, les suggestions seront
            // rechargées afin d'afficher la nouvelle distance entre la suggestion et l'emplacement
            // actuel
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    // Fonction executée après avoir recuperer les suggestions (en JSON), cette fonction permet de
    // charger les suggestions
    public void addSuggestions (City c) {
        gs.selectedCity = c;
        setAdapter();
        // La liste des suggestions est ensuite enregistrée dans les shared preferences
        SharedPreferences.Editor editor = gs.prefs.edit();
        editor.apply();
    }

    // Fonction permettant d'initialiser le recyclerview
    public void setAdapter() {
        rvSuggestions=(RecyclerView)findViewById(R.id.rvSuggestions);
        llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rvSuggestions.setLayoutManager(llm);
        rvSuggestions.setHasFixedSize(true);
        adapter = new RVASuggestion(gs);
        rvSuggestions.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picTest:
                gs.alerter("coucou2");
                Intent toSuggestions = new Intent(this, SuggestActivity.class);
                startActivity(toSuggestions);
                gs.alerter("coucou");

                break;
            case R.id.retour:
                this.finish();
                break;
        }
    }

}
