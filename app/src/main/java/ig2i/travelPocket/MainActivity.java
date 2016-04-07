package ig2i.travelPocket;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity implements View.OnClickListener   {

    GlobalState gs;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private RecyclerView rvCities;
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
        refreshBttn = (MenuItem)findViewById(R.id.action_refresh);

        rvCities=(RecyclerView)findViewById(R.id.rvCities);

        llm = new LinearLayoutManager(this);
        rvCities.setLayoutManager(llm);
        rvCities.setHasFixedSize(true);

        Fresco.initialize(this);

        gson = new Gson();

        initializeAdapter();

        adapter.setOnItemClickListener(new RVAWeather
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                gs.alerter(" Clicked on Item " + position);
                gs.selectedCity = gs.cities.get(position);
                goToInfoCity();
            }
        });

        swipeTouchListener();

        testParams();

        updateEverything();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void testParams() {
        if(gs.prefs.contains("placesType")) {

            Set<String> params = gs.prefs.getStringSet("placesType", null);

            String out = "";

            if (params != null) {
                for (String p : params
                        ) {
                    out = out + " " + p;
                }
            }

            gs.alerter(out);
        }
    }

    private void goToInfoCity(){
        Intent toInfoCity = new Intent(this, InfoCity.class);
        startActivity(toInfoCity);
    }

    private void initializeAdapter(){
        adapter = new RVAWeather(gs.cities);
        rvCities.setAdapter(adapter);
    }

    private void majAdapter(){
        adapter.notifyDataSetChanged();
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
                gs.alerter("Clic sur Preferences");
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
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                break;
            case R.id.action_refresh :
                //refreshBttn.setEnabled(false);
                updateEverything();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void updatePrefs() {
        String json = gson.toJson(gs.cities);
        gs.prefs.edit().putString("cities", json).commit();
       // gs.prefs.edit().remove("latlongs").commit();
        gs.prefs.edit().putStringSet("latlongs", gs.latlongs).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

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
                    JSONWeatherInfo w = new JSONWeatherInfo("add",latlong, this, city, pays);
                    w.execute();
                } else {
                    gs.alerter(city + ", " + pays + " existe déjà");
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                gs.alerter(status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void addCity() {
        adapter.notifyItemInserted(gs.cities.size()-1);
        majAdapter();
    }

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
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {

                        }
                    }
                });

        rvCities.addOnItemTouchListener(swipeTouchListener);
    }

    public void updateWeather(City c) {
        latlong = c.latitude + "," + c.longitude;
        city = c.name;
        pays = c.pays;
        JSONWeatherInfo w = new JSONWeatherInfo("update",latlong, this, city, pays);
        w.execute();
    }

    public void update (City result) {
        City old = new City();
        for(City c : gs.cities) {
            if(c.latitude.equals(result.latitude)) {
                old = c;
            }
        }
        old.update(result);
        majAdapter();
    }

    public void updateEverything(){
        for(City c : gs.cities) {
            updateWeather(c);
        }
        updatePrefs();
    }

}