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

    TextView dayToday;
    TextView tempMinToday;
    TextView tempMaxToday;
    SimpleDraweeView picToday;

    TextView dayDay1;
    TextView tempMinDay1;
    TextView tempMaxDay1;
    SimpleDraweeView picDay1;

    TextView dayDay2;
    TextView tempMinDay2;
    TextView tempMaxDay2;
    SimpleDraweeView picDay2;

    TextView dayDay3;
    TextView tempMinDay3;
    TextView tempMaxDay3;
    SimpleDraweeView picDay3;

    TextView dayDay4;
    TextView tempMinDay4;
    TextView tempMaxDay4;
    SimpleDraweeView picDay4;

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

        dayToday = (TextView)findViewById(R.id.dayToday);
        tempMinToday = (TextView)findViewById(R.id.tempMinToday);
        tempMaxToday = (TextView)findViewById(R.id.tempMaxToday);
        picToday = (SimpleDraweeView)findViewById(R.id.picToday);

        dayDay1 = (TextView)findViewById(R.id.dayDay1);
        tempMinDay1 = (TextView)findViewById(R.id.tempMinDay1);
        tempMaxDay1 = (TextView)findViewById(R.id.tempMaxDay1);
        picDay1 = (SimpleDraweeView)findViewById(R.id.picDay1);

        dayDay2 = (TextView)findViewById(R.id.dayDay2);
        tempMinDay2 = (TextView)findViewById(R.id.tempMinDay2);
        tempMaxDay2 = (TextView)findViewById(R.id.tempMaxDay2);
        picDay2 = (SimpleDraweeView)findViewById(R.id.picDay2);

        dayDay3 = (TextView)findViewById(R.id.dayDay3);
        tempMinDay3 = (TextView)findViewById(R.id.tempMinDay3);
        tempMaxDay3 = (TextView)findViewById(R.id.tempMaxDay3);
        picDay3 = (SimpleDraweeView)findViewById(R.id.picDay3);

        dayDay4 = (TextView)findViewById(R.id.dayDay4);
        tempMinDay4 = (TextView)findViewById(R.id.tempMinDay4);
        tempMaxDay4 = (TextView)findViewById(R.id.tempMaxDay4);
        picDay4 = (SimpleDraweeView)findViewById(R.id.picDay4);

        ville.setText(gs.selectedCity.name);
        temps.setText(gs.selectedCity.description);
        currentWeather.setText(gs.selectedCity.currentWeather);
        Uri uri = Uri.parse(gs.selectedCity.picture);
        picture.setImageURI(uri);

        dayToday.setText(gs.selectedCity.daily.get(0).date);
        dayDay1.setText(gs.selectedCity.daily.get(1).date);
        dayDay2.setText(gs.selectedCity.daily.get(2).date);
        dayDay3.setText(gs.selectedCity.daily.get(3).date);
        dayDay4.setText(gs.selectedCity.daily.get(4).date);

        tempMinToday.setText(gs.selectedCity.daily.get(0).tempMin);
        tempMinDay1.setText(gs.selectedCity.daily.get(1).tempMin);
        tempMinDay2.setText(gs.selectedCity.daily.get(2).tempMin);
        tempMinDay3.setText(gs.selectedCity.daily.get(3).tempMin);
        tempMinDay4.setText(gs.selectedCity.daily.get(4).tempMin);

        tempMaxToday.setText(gs.selectedCity.daily.get(0).tempMax);
        tempMaxDay1.setText(gs.selectedCity.daily.get(1).tempMax);
        tempMaxDay2.setText(gs.selectedCity.daily.get(2).tempMax);
        tempMaxDay3.setText(gs.selectedCity.daily.get(3).tempMax);
        tempMaxDay4.setText(gs.selectedCity.daily.get(4).tempMax);


        picToday.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(0).icon, null, getPackageName())));
        picDay1.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(1).icon, null, getPackageName())));
        picDay2.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(2).icon, null, getPackageName())));
        picDay3.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(3).icon, null, getPackageName())));
        picDay4.setImageDrawable(getResources().getDrawable(getResources().
                getIdentifier("@drawable/" +
                        gs.selectedCity.daily.get(4).icon, null, getPackageName())));

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
