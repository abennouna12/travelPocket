package ig2i.travelPocket.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import ig2i.travelPocket.R;

public class PreferencesActivity extends PreferenceActivity{

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addPreferencesFromResource(R.xml.preferences);
    }


}
