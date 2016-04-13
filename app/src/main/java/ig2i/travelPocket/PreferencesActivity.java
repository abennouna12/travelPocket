package ig2i.travelPocket;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by aBennouna on 07/03/2016.
 */
public class PreferencesActivity extends PreferenceActivity{

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addPreferencesFromResource(R.xml.preferences);
    }


}
