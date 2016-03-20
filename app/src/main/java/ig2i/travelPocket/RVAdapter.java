package ig2i.travelPocket;

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

/**
 * Created by aBennouna on 11/03/2016.
 */


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CityViewHolder> {


    private static MyClickListener myClickListener;

    public static class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView name;
        TextView currentWeather;
        TextView pays;
        ImageView picture;

        public CityViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
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

    RVAdapter(List<City> cities){
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        CityViewHolder cvh = new CityViewHolder(v);
        return cvh;
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
        return cities.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
