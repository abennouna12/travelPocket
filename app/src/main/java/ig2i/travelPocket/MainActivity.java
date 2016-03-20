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

    ImageButton btnAdd;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private RecyclerView rvCities;
    private StaggeredGridLayoutManager m;
    String image;
    String city;
    String pays;
    String description;
    String latlong;
    String currentWeather;
    RVAdapter adapter;
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gs = (GlobalState) getApplication();

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

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    public void setWeather(List<String> list){
        this.currentWeather = list.get(0);
        this.image = list.get(1);
        this.description = list.get(2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Double lat = place.getLatLng().latitude;
                Double lon = place.getLatLng().longitude;
                latlong = "lat=" + lat + "&lon=" + lon;
                city = place.getName().toString();
                String[] address = place.getAddress().toString().split(",");
                pays = address[ address.length - 1 ].toUpperCase().substring(1);
                if(!(gs.latlongs.contains(latlong))) {
                    WeatherInfo w = new WeatherInfo(latlong, this, city);
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
        gs.cities.add(new City(pays, city, description, currentWeather + "°", image));
        gs.latlongs.add(latlong);
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
                     //
                    }
                });

        rvCities.addOnItemTouchListener(swipeTouchListener);
    }


}