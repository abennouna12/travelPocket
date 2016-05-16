package ig2i.travelPocket.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.adapter.SuggestTypeAdapter;
import ig2i.travelPocket.model.SuggestionType;

public class AddSuggestionType extends Activity implements View.OnClickListener {

    ListView lv;
    EditText et;
    Button btn;
    SuggestTypeAdapter adapter;
    public GlobalState gs;
    Gson gson;
    List<SuggestionType> selectedTypes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gs = (GlobalState) getApplication();
        gson = new Gson();
        selectedTypes = new ArrayList<>();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_suggestion_type);

        lv = (ListView) findViewById(R.id.SuggestGoogleNew);
        et = (EditText) findViewById(R.id.NewSuggestionUser);
        btn = (Button) findViewById(R.id.AddNewSuggestButton);

        gs.virginGoogleSuggestions = gs.getVirginSuggestions();

        adapter = new SuggestTypeAdapter(gs, this, gs.virginGoogleSuggestions,false);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long arg3) {
                SuggestionType suggest = (SuggestionType) parent.getItemAtPosition(position);
                suggest.checked = !(suggest.getChecked());
                majAdapter();

                if(suggest.getChecked()) {
                    if (!selectedTypes.contains(suggest)) {
                        selectedTypes.add(suggest);
                    }
                } else {
                    if (selectedTypes.contains(suggest)) {
                        selectedTypes.remove(suggest);
                    }
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AddNewSuggestButton:
                SuggestionType newSuggest = new SuggestionType();
                newSuggest.checked = true;
                newSuggest.name = et.getText().toString();
                if(gs.UserSuggestions.contains(newSuggest)) {
                    gs.alerter("Le filtre existe deja");
                } else {
                    if (newSuggest.name.isEmpty()) {
                        gs.alerter("Veuillez saisir le nom du filtre");
                    } else {
                        if (newSuggest.name.equals("")) {
                            gs.alerter("Veuillez saisir le nom du filtre");
                        } else {
                            newSuggest.value = gs.getTypeValues(selectedTypes);
                            gs.UserSuggestions.add(newSuggest);
                            gs.updatePrefsSuggestions();
                            this.finish();
                            Intent toSuggestions = new Intent(this, SuggestActivity.class);
                            startActivity(toSuggestions);
                            gs.alerter(newSuggest.getValue());
                        }
                    }
                }
                break;
        }
    }



    private void majAdapter() {
        adapter.notifyDataSetChanged();
    }

}
