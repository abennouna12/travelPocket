package ig2i.travelPocket;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by aBennouna on 07/03/2016.
 */
public class PreferencesActivity extends PreferenceActivity{
    GlobalState gs;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        gs = (GlobalState) getApplication();
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }


}
