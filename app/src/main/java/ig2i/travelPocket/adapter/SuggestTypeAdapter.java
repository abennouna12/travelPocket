package ig2i.travelPocket.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.List;

import ig2i.travelPocket.GlobalState;
import ig2i.travelPocket.R;
import ig2i.travelPocket.model.FilterType;


public class SuggestTypeAdapter extends ArrayAdapter<FilterType>{
    List<FilterType> types = null;
    Context context;
    GlobalState gs;
    Boolean isDeletable;
    public SuggestTypeAdapter(GlobalState gs,Context context, List<FilterType> resource, Boolean isDeletable) {
        super(context,R.layout.filter_item,resource);
        this.context = context;
        this.types = resource;
        this.gs = gs;
        this.isDeletable = isDeletable;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.filter_item, parent, false);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.CheckboxSuggestType);
        ImageView delete = (ImageView) convertView.findViewById(R.id.deleteSuggestButton);
        if (isDeletable) {
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    types.remove(position);
                    notifyDataSetChanged();
                    gs.updatePrefsSuggestions();
                }
            });
        } else {
            delete.setVisibility(View.INVISIBLE);
        }
        cb.setText(types.get(position).getName());
        cb.setChecked(types.get(position).getChecked());
        return convertView;
    }
}


