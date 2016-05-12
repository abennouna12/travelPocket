package ig2i.travelPocket.activity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.adapter.SuggestTypeAdapter;
import ig2i.travelPocket.model.SuggestionType;

public class SuggestActivity extends Activity {

    ListView lv,lv2;
    public GlobalState gs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gs = (GlobalState) getApplication();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggest);

        lv = (ListView) findViewById(R.id.SuggestGoogle);
        SuggestTypeAdapter adapter = new SuggestTypeAdapter(this, gs.GoogleSuggestions);
        lv.setAdapter(adapter);

        lv2 = (ListView) findViewById(R.id.SuggestUser);
        SuggestTypeAdapter adapter2 = new SuggestTypeAdapter(this, gs.UserSuggestions);
        lv2.setAdapter(adapter2);
    }
}
