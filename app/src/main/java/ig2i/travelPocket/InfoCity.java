package ig2i.travelPocket;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class InfoCity extends Activity implements View.OnClickListener {

    GlobalState gs;
    TextView temps;
    TextView ville;
    TextView currentWeather;
    SimpleDraweeView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //enlever le titre
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_info_city);
        gs = (GlobalState) getApplication();
        Fresco.initialize(this);

        temps = (TextView)findViewById(R.id.temps);
        ville = (TextView)findViewById(R.id.ville);
        currentWeather = (TextView)findViewById(R.id.currentWeather);
        picture = (SimpleDraweeView)findViewById(R.id.picture);

        ville.setText(gs.selectedCity.name);
        temps.setText(gs.selectedCity.description);
        currentWeather.setText(gs.selectedCity.currentWeather);
        Uri uri = Uri.parse(gs.selectedCity.picture);
        picture.setImageURI(uri);

        //Remove title bar

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retour:
                Intent toMainActivity = new Intent(this, MainActivity.class);
                startActivity(toMainActivity);
                break;
        }
    }
}
