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
import android.support.v7.widget.RecyclerView;
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
import ig2i.travelPocket.model.Suggestion;

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
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            openClose = (TextView) itemView.findViewById(R.id.openClose);
            distance = (TextView) itemView.findViewById(R.id.distanceText);
            picture = (SimpleDraweeView) itemView.findViewById(R.id.picture_place_suggest);
            phoneIcon = (ImageView) itemView.findViewById(R.id.phoneIcon);
            webIcon = (ImageView) itemView.findViewById(R.id.webIcon);
            mapIcon = (ImageView) itemView.findViewById(R.id.mapIcon);
            openCloseLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutOpenClose);
        }

    }

    List<Suggestion> suggestions;

    GlobalState gs;

    public RVASuggestion(GlobalState gs) {
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
        return new SuggestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder suggestionViewHolder, int i) {

        if (suggestions.get(i).rating != (float)0) {
            int numStars = (int) Math.ceil((Double.valueOf(suggestions.get(i).rating)));
            suggestionViewHolder.rating.setNumStars(numStars);
            suggestionViewHolder.rating.setRating(suggestions.get(i).rating);
        } else {
            suggestionViewHolder.rating.setNumStars(0);
            suggestionViewHolder.rating.setRating((float) 0);
            suggestionViewHolder.rating.setVisibility(View.GONE);
        }

        suggestionViewHolder.placeName.setText(suggestions.get(i).placeName);

        final Location depart = new Location("");
        depart.setLatitude(Double.parseDouble(gs.prefs.getString("currentLat", "0.0")));
        depart.setLongitude(Double.parseDouble(gs.prefs.getString("currentLng", "0.0")));

        final Location arrivee = new Location("");
        arrivee.setLatitude(Double.parseDouble(suggestions.get(i).latitude));
        arrivee.setLongitude(Double.parseDouble(suggestions.get(i).longitude));

        String distance = String.format("%.2f", depart.distanceTo(arrivee) / 1000) + "kms";
        suggestionViewHolder.openClose.setText(suggestions.get(i).openClose);
        suggestionViewHolder.distance.setText(distance);

        setOpenCloseColor(suggestionViewHolder,i);

        Uri uri = Uri.parse(suggestions.get(i).picture);
        suggestionViewHolder.picture.setImageURI(uri);

        final String currentPhone = suggestions.get(i).phone;
        final String currentName = suggestions.get(i).placeName;
        final String currentWeb = suggestions.get(i).web;
        final String currentLatitude = suggestions.get(i).latitude;
        final String currentLongitude = suggestions.get(i).longitude;

        if (!currentPhone.isEmpty()) {
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
                            }
                        })
                        .show();
            }
        });

        if (!currentWeb.isEmpty()) {
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

    // Fonction permettant de changer la couleur de l'horaire d'ouverture
    public void setOpenCloseColor(SuggestionViewHolder suggestionViewHolder, int i) {
        if (suggestions.get(i).openClose.equals("Ouvert")) {
            suggestionViewHolder.openCloseLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_bg_green));
        } else if (suggestions.get(i).openClose.equals("Fermé")) {
            suggestionViewHolder.openCloseLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_bg_red));
        } else {
            suggestionViewHolder.openCloseLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rounded_bg_orange));
        }
    }


}
