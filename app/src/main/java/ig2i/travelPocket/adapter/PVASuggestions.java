package ig2i.travelPocket.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.model.PictureInfo;
import ig2i.travelPocket.model.Suggestion;

/**
 * Created by aBennouna on 18/05/2016.
 */
public class PVASuggestions extends PagerAdapter {

    Context mContext;
    GlobalState gs;
    List<Suggestion> suggestions;
    private LayoutInflater layoutInflater;

    TextView placeName;
    RatingBar rating;
    TextView openClose;
    TextView distance;
    ImageView picture;
    RelativeLayout openCloseLayout;
    ImageView phoneIcon;
    ImageView webIcon;
    ImageView mapIcon;


    public PVASuggestions (Context mContext, GlobalState gs) {
        this.mContext = mContext;
        this.gs = gs;
        this.suggestions = gs.selectedCity.suggestions;
    }

    @Override
    public int getCount() {
        return (suggestions == null) ? 0 : suggestions.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.suggestion_item,container,false);

        placeName = (TextView) itemView.findViewById(R.id.placeName);
        rating = (RatingBar) itemView.findViewById(R.id.rating);
        openClose = (TextView) itemView.findViewById(R.id.openClose);
        distance = (TextView) itemView.findViewById(R.id.distanceText);
        picture = (SimpleDraweeView) itemView.findViewById(R.id.picture_place_suggest);
        phoneIcon = (ImageView) itemView.findViewById(R.id.phoneIcon);
        webIcon = (ImageView) itemView.findViewById(R.id.webIcon);
        mapIcon = (ImageView) itemView.findViewById(R.id.mapIcon);
        openCloseLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutOpenClose);

        if (suggestions.get(i).rating != (float)0) {
            int numStars = (int) Math.ceil((Double.valueOf(suggestions.get(i).rating)));
            rating.setNumStars(numStars);
            rating.setRating(suggestions.get(i).rating);
        } else {
            rating.setNumStars(0);
            rating.setRating((float) 0);
            rating.setVisibility(View.GONE);
        }

        placeName.setText(suggestions.get(i).placeName);

        final Location depart = new Location("");
        depart.setLatitude(Double.parseDouble(gs.prefs.getString("currentLat", "0.0")));
        depart.setLongitude(Double.parseDouble(gs.prefs.getString("currentLng", "0.0")));

        final Location arrivee = new Location("");
        arrivee.setLatitude(Double.parseDouble(suggestions.get(i).latitude));
        arrivee.setLongitude(Double.parseDouble(suggestions.get(i).longitude));

        String dist = String.format("%.2f", depart.distanceTo(arrivee) / 1000) + "kms";
        openClose.setText(suggestions.get(i).openClose);
        distance.setText(dist);

        setOpenCloseColor(i);

        Uri uri = Uri.parse(suggestions.get(i).picture);
        picture.setImageURI(uri);

        final String currentPhone = suggestions.get(i).phone;
        final String currentName = suggestions.get(i).placeName;
        final String currentWeb = suggestions.get(i).web;
        final String currentLatitude = suggestions.get(i).latitude;
        final String currentLongitude = suggestions.get(i).longitude;

        if (!currentPhone.isEmpty()) {
            phoneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("Voulez vous vraiment appeler " + currentName + " ?")
                            .setNegativeButton("Non", null)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String number = "tel:" + currentPhone;
                                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    mContext.startActivity(callIntent);
                                }
                            })
                            .show();
                }
            });
        } else {
            phoneIcon.setVisibility(View.INVISIBLE);
        }


        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Voulez vous vraiment ouvrir " + currentName + " sur Google Maps ?")
                        .setNegativeButton("Non", null)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?q=loc:" + currentLatitude + "," + currentLongitude));
                                mContext.startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage("Voulez vous vraiment ouvrir l'itineraire pour " + currentName + " sur Google Maps ?")

                        .setNegativeButton("Non", null)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=" + depart.getLatitude() +
                                                "," + depart.getLongitude() + "&daddr=" + arrivee.getLatitude() +
                                                "," + arrivee.getLongitude()));
                                mContext.startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        if (!currentWeb.isEmpty()) {
            webIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(currentWeb));
                    mContext.startActivity(intent);
                }
            });
        } else {
            webIcon.setVisibility(View.INVISIBLE);
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

    public void setOpenCloseColor(int i) {
        if (suggestions.get(i).openClose.equals("Ouvert")) {
            openCloseLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_bg_green));
        } else if (suggestions.get(i).openClose.equals("Fermé")) {
            openCloseLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_bg_red));
        } else {
            openCloseLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_bg_orange));
        }
    }
}
