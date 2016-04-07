package ig2i.travelPocket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Set;

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
