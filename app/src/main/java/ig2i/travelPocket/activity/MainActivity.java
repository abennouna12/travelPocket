package ig2i.travelPocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.Gson;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.adapter.RVAWeather;
import ig2i.travelPocket.model.City;
import ig2i.travelPocket.service.JSONWeatherInfo;

public class MainActivity extends Activity {

    GlobalState gs;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    RecyclerView rvCities;
    ImageView pictureNoCity;
    String city;
    String pays;
    String latlong;
    RVAWeather adapter;
    LinearLayoutManager llm;
    MenuItem refreshBttn;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gs = (GlobalState) getApplication();
        refreshBttn = (MenuItem) findViewById(R.id.action_refresh);

        pictureNoCity = (ImageView) findViewById(R.id.pictureNoCity);
        pictureNoCity.setImageResource(R.drawable.no_weather_image);

        rvCities = (RecyclerView) findViewById(R.id.rvCities);

        llm = new LinearLayoutManager(this);
        rvCities.setLayoutManager(llm);
        rvCities.setHasFixedSize(true);

        Fresco.initialize(this);

        gson = new Gson();

        initializeAdapter();

        // Click listener
        adapter.setOnItemClickListener(new RVAWeather
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                gs.selectedCity = gs.cities.get(position);
                goToInfoCity();
            }
        });

        swipeTouchListener();

        updateEverything();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void handleIfRecylerIsEmpty(){
        if(gs.cities.isEmpty()) {
            rvCities.setVisibility(View.GONE);
            pictureNoCity.setVisibility(View.VISIBLE);
        } else {
            rvCities.setVisibility(View.VISIBLE);
            pictureNoCity.setVisibility(View.GONE);
        }
    }

    // Fonction pour aller a l'activité InfoCityActivity
    private void goToInfoCity(){
        Intent toInfoCity = new Intent(this, InfoCityActivity.class);
        startActivity(toInfoCity);
    }

    // Fonction pour initialiser le recyclerview adapter
    private void initializeAdapter(){
        adapter = new RVAWeather(gs.cities);
        rvCities.setAdapter(adapter);
        handleIfRecylerIsEmpty();
    }

    // Fonction pour mettre a jour l'adapter
    private void majAdapter(){
        adapter.notifyDataSetChanged();
        handleIfRecylerIsEmpty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_settings :
                Intent versPreferences = new Intent(this,PreferencesActivity.class);
                startActivity(versPreferences);
                break;
            case R.id.action_add :
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .setFilter(typeFilter)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e("RepairableException",e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e("NotAvailableException", e.getMessage());
                }
                break;
            case R.id.action_refresh :
                updateEverything();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    // Update shared preferences
    public void updatePrefs() {
        SharedPreferences.Editor editor = gs.prefs.edit();
        editor.putString("cities", gson.toJson(gs.cities));
        editor.putStringSet("latlongs", gs.latlongs);
        editor.apply();
    }

    // Fonction executee lors de l'ajout d'une ville
    public void setWeather(City result){
        gs.cities.add(result);
        gs.latlongs.add(latlong);
        updatePrefs();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Double lat = place.getLatLng().latitude;
                Double lon = place.getLatLng().longitude;
                latlong = lat + "," + lon;
                city = place.getName().toString();
                String[] address = place.getAddress().toString().split(",");
                pays = address[ address.length - 1 ].toUpperCase().substring(1);
                if(!(gs.latlongs.contains(latlong))) {
                    JSONWeatherInfo w = new JSONWeatherInfo(gs,"add",latlong, this, city, pays);
                    w.execute();
                } else {
                    gs.alerter(city + ", " + pays + " existe déjà");
                }

            }
        }
    }

    // Add a city and update adapter
    public void addCity() {
        adapter.notifyItemInserted(gs.cities.size()-1);
        majAdapter();
    }

    // Swipe touch listener to delete a city while swipping left
    public void swipeTouchListener()
    {
        SwipeableRecyclerViewTouchListener swipeTouchListener =
            new SwipeableRecyclerViewTouchListener(rvCities,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipeLeft(int position) {
                        return true;
                    }

                    @Override
                    public boolean canSwipeRight(int position) {
                        return false;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            City c = gs.cities.get(position);
                            gs.latlongs.remove(c.latitude + "," + c.longitude);
                            gs.cities.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                        updatePrefs();
                        majAdapter();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {

                    }
                });

        rvCities.addOnItemTouchListener(swipeTouchListener);
    }

    // Fonction pour mettre a jour une ville
    public void updateCity(City c) {
        latlong = c.latitude + "," + c.longitude;
        city = c.name;
        pays = c.pays;
        JSONWeatherInfo w = new JSONWeatherInfo(gs,"update",latlong, this, city, pays);
        w.execute();
    }

    // Fonction appellee apres avoir recuperer la nouvelle ville
    public void update (City result) {
        City old = new City();
        for(City c : gs.cities) {
            if(c.latitude.equals(result.latitude)) {
                old = c;
            }
        }
        old.update(result,gs.isPhotosUpdatable());
        majAdapter();
    }

    // Fonction permettant de mettre a jour toutes les villes
    public void updateEverything(){

        if (gs.isConnected()) {
            if (gs.cities.size() != 0) {
                for (City c : gs.cities) {
                    updateCity(c);
                }
                updatePrefs();
            }
        } else {
            gs.alerter("Veuillez verifier votre connexion");
        }

    }

}