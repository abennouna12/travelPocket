package ig2i.travelPocket.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ig2i.travelPocket.R;
import ig2i.travelPocket.model.City;

public class RVAWeather extends RecyclerView.Adapter<RVAWeather.CityViewHolder> {


    private MyClickListener myClickListener;

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv_weather;
        TextView name;
        TextView currentWeather;
        TextView pays;
        ImageView picture;

        public CityViewHolder(View itemView) {
            super(itemView);
            cv_weather = (CardView)itemView.findViewById(R.id.cv_weather);
            name = (TextView)itemView.findViewById(R.id.name);
            pays = (TextView)itemView.findViewById(R.id.pays);
            currentWeather = (TextView)itemView.findViewById(R.id.currentWeather);
            picture = (SimpleDraweeView)itemView.findViewById(R.id.picture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    List<City> cities;

    public RVAWeather(List<City> cities){
        this.cities = cities;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_item, viewGroup, false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CityViewHolder cityViewHolder, int i) {
        cityViewHolder.name.setText(cities.get(i).name);
        cityViewHolder.pays.setText(cities.get(i).pays);
        cityViewHolder.currentWeather.setText(cities.get(i).currentWeather);
        Uri uri = Uri.parse(cities.get(i).picture);
        cityViewHolder.picture.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return (cities == null) ? 0 : cities.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

}
