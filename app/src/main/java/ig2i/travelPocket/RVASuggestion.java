package ig2i.travelPocket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by aBennouna on 11/03/2016.
 */


public class RVASuggestion extends RecyclerView.Adapter<RVASuggestion.SuggestionViewHolder> {

    Context mContext;

    public static class SuggestionViewHolder extends RecyclerView.ViewHolder {

        TextView placeName;
        RatingBar rating;
        TextView openClose;
        TextView distance;
        ImageView picture;
        RelativeLayout openCloseLayout;
        ImageView phoneIcon;
        ImageView webIcon;
        ImageView mapIcon;

        public SuggestionViewHolder(View itemView) {
            super(itemView);
            placeName = (TextView)itemView.findViewById(R.id.placeName);
            rating = (RatingBar)itemView.findViewById(R.id.rating);
            openClose = (TextView)itemView.findViewById(R.id.openClose);
            distance = (TextView)itemView.findViewById(R.id.distanceText);
            picture = (SimpleDraweeView)itemView.findViewById(R.id.picture_place_suggest);
            phoneIcon = (ImageView)itemView.findViewById(R.id.phoneIcon);
            webIcon = (ImageView)itemView.findViewById(R.id.webIcon);
            mapIcon = (ImageView)itemView.findViewById(R.id.mapIcon);
            openCloseLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayoutOpenClose);
        }

    }

    List<Suggestion> suggestions;

    GlobalState gs;

    RVASuggestion(GlobalState gs){
        this.gs = gs;
        this.suggestions = gs.selectedCity.suggestions;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suggestion_item, viewGroup, false);
        mContext = viewGroup.getContext();
        SuggestionViewHolder svh = new SuggestionViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder suggestionViewHolder, int i) {
        suggestionViewHolder.rating.setRating(suggestions.get(i).rating);
        suggestionViewHolder.placeName.setText(suggestions.get(i).placeName);

        final Location depart = new Location("");
        depart.setLatitude(Double.parseDouble(gs.prefs.getString("currentLat", "0.0")));
        depart.setLongitude(Double.parseDouble(gs.prefs.getString("currentLng", "0.0")));

        final Location arrivee = new Location("");
        arrivee.setLatitude(Double.parseDouble(suggestions.get(i).latitude));
        arrivee.setLongitude(Double.parseDouble(suggestions.get(i).longitude));

        float distance = depart.distanceTo(arrivee) / 1000;

        suggestionViewHolder.openClose.setText(suggestions.get(i).openClose);
        suggestionViewHolder.distance.setText(String.format("%.2f", distance) + "kms");

        if(suggestions.get(i).openClose=="Ouvert") {
            suggestionViewHolder.openCloseLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_bg_green));
        } else if(suggestions.get(i).openClose=="Fermé"){
            suggestionViewHolder.openCloseLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_bg_red));
        } else {
            suggestionViewHolder.openCloseLayout.setBackground(ContextCompat.getDrawable(mContext,R.drawable.rounded_bg_orange));
        }
        Uri uri = Uri.parse(suggestions.get(i).picture);
        suggestionViewHolder.picture.setImageURI(uri);

        final String currentPhone = suggestions.get(i).phone;
        final String currentName = suggestions.get(i).placeName;
        final String currentWeb = suggestions.get(i).web;
        final String currentLatitude = suggestions.get(i).latitude;
        final String currentLongitude = suggestions.get(i).longitude;

        if(currentPhone != "") {
            suggestionViewHolder.phoneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("Voulez vous vraiment appeler " + currentName + " ?")
                            .setNegativeButton("Non", null)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String number = "tel:" + currentPhone;
                                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                                    mContext.startActivity(callIntent);
                                }
                            })
                            .show();
                }
            });
        } else {
            suggestionViewHolder.phoneIcon.setVisibility(View.INVISIBLE);
        }


        suggestionViewHolder.mapIcon.setOnClickListener(new View.OnClickListener() {
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
                    }})
                .show();
            }
        });

        suggestionViewHolder.distance.setOnClickListener(new View.OnClickListener() {
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
                            }})
                        .show();
            }
        });

        if (currentWeb != "") {
            suggestionViewHolder.webIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(currentWeb));
                    mContext.startActivity(intent);
                }
            });
        } else {
            suggestionViewHolder.webIcon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return (suggestions == null) ? 0 : suggestions.size();
    }


}
