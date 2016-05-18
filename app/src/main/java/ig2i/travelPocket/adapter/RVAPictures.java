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
import ig2i.travelPocket.model.PictureInfo;
import me.grantland.widget.AutofitTextView;

public class RVAPictures extends RecyclerView.Adapter<RVAPictures.PictureViewHolder> {

    public class PictureViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        AutofitTextView description;
        TextView date;
        ImageView picture;

        public PictureViewHolder(View itemView) {
            super(itemView);
            description = (AutofitTextView)itemView.findViewById(R.id.PictureDescription);
            author = (TextView)itemView.findViewById(R.id.PictureAuthor);
            date = (TextView)itemView.findViewById(R.id.PictureDate);
            picture = (SimpleDraweeView)itemView.findViewById(R.id.PictureSrc);
        }
    }

    List<PictureInfo> pictures;

    public RVAPictures(List<PictureInfo> pictures){
        this.pictures = pictures;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.picture_item, viewGroup, false);
        return new PictureViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder cityViewHolder, int i) {
        cityViewHolder.author.setText(pictures.get(i).author);
        //cityViewHolder.description.setText(pictures.get(i).description);
        cityViewHolder.description.setText("");
        cityViewHolder.date.setText(pictures.get(i).takenOn.toString());
        Uri uri = Uri.parse(pictures.get(i).src);
        cityViewHolder.picture.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return (pictures == null) ? 0 : pictures.size();
    }


}
