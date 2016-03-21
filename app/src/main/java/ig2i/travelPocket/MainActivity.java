package ig2i.travelPocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener   {

    GlobalState gs;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private RecyclerView rvCities;
    String city;
    String pays;
    String latlong;
    RVAdapter adapter;
    LinearLayoutManager llm;
    MenuItem refreshBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gs = (GlobalState) getApplication();
        refreshBttn = (MenuItem)findViewById(R.id.action_refresh);

        //gs.alerter("onCreate");


        rvCities=(RecyclerView)findViewById(R.id.rvCities);

        llm = new LinearLayoutManager(this);
        rvCities.setLayoutManager(llm);
        rvCities.setHasFixedSize(true);

        Fresco.initialize(this);

        if (gs.cities==null) {
            gs.cities = new ArrayList<>();
        }
        if (gs.latlongs==null) {
            gs.latlongs = new ArrayList<>();
        }
        initializeAdapter();

        adapter.setOnItemClickListener(new RVAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                gs.alerter(" Clicked on Item " + position);
                gs.selectedCity = gs.cities.get(position);
                goToInfoCity();
            }
        });

        swipeTouchListener();


    }

    private void goToInfoCity(){
        Intent toInfoCity = new Intent(this, InfoCity.class);
        startActivity(toInfoCity);
    }

    private void initializeAdapter(){
        adapter = new RVAdapter(gs.cities);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    public void setWeather(City result){
        gs.cities.add(result);
        gs.latlongs.add(latlong);
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
                    WeatherInfo w = new WeatherInfo("add",latlong, this, city, pays);
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
                            gs.cities.remove(position);
                            gs.latlongs.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
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
        WeatherInfo w = new WeatherInfo("update",latlong, this, city, pays);
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
        //refreshBttn.setEnabled(true);
    }




}
