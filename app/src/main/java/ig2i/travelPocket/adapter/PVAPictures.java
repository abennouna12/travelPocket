package ig2i.travelPocket.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.model.PictureInfo;
import me.grantland.widget.AutofitTextView;

public class PVAPictures extends PagerAdapter {

    Context mContext;
    GlobalState gs;
    List<PictureInfo> pictures;
    LayoutInflater layoutInflater;

    TextView author;
    AutofitTextView description;
    TextView date;
    ImageView picture;


    public PVAPictures(Context mContext, GlobalState gs) {
        this.mContext = mContext;
        this.gs = gs;
        this.pictures = gs.selectedCity.pictures;
    }

    @Override
    public int getCount() {
        return (pictures == null) ? 0 : pictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.picture_item,container,false);

        description = (AutofitTextView)itemView.findViewById(R.id.PictureDescription);
        author = (TextView)itemView.findViewById(R.id.PictureAuthor);
        date = (TextView)itemView.findViewById(R.id.PictureDate);
        picture = (SimpleDraweeView)itemView.findViewById(R.id.PictureSrc);
        String copyright = "© " + pictures.get(i).author;
        author.setText(copyright);
        //description.setText(pictures.get(i).description);
        description.setText("");

        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.FRANCE);
        date.setText(dateFormatter.format(pictures.get(i).takenOn));
        Uri uri = Uri.parse(pictures.get(i).src);
        picture.setImageURI(uri);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }


}
