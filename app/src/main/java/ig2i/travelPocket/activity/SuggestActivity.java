package ig2i.travelPocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;


import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.adapter.SuggestTypeAdapter;
import ig2i.travelPocket.model.SuggestionType;

public class SuggestActivity extends Activity implements View.OnClickListener {

    ListView lvGoogle,lvUser;
    SuggestTypeAdapter adapterGoogle, adapterUser;
    public GlobalState gs;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gs = (GlobalState) getApplication();
        gson = new Gson();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggest);

        lvGoogle = (ListView) findViewById(R.id.SuggestGoogle);
        adapterGoogle = new SuggestTypeAdapter(gs, this, gs.GoogleSuggestions,false);
        lvGoogle.setAdapter(adapterGoogle);

        lvUser = (ListView) findViewById(R.id.SuggestUser);
        adapterUser = new SuggestTypeAdapter(gs, this, gs.UserSuggestions,true);
        lvUser.setAdapter(adapterUser);

        lvGoogle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long arg3) {

                SuggestionType suggest = (SuggestionType) parent.getItemAtPosition(position);
                suggest.checked = !(suggest.getChecked());

                majAdapter();
                gs.updatePrefsSuggestions();
            }
        });

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long arg3) {

                SuggestionType suggest = (SuggestionType) parent.getItemAtPosition(position);
                suggest.checked = !(suggest.getChecked());
                majAdapter();
                gs.updatePrefsSuggestions();
            }
        });


    }

    private void majAdapter(){
        adapterGoogle.notifyDataSetChanged();
        adapterUser.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonAddSuggestion:
                this.finish();
                Intent newSuggest = new Intent(this, AddSuggestionType.class);
                startActivity(newSuggest);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
       /* Intent toInfoCity = new Intent(this, InfoCityActivity.class);
        startActivity(toInfoCity);*/
    }
}
