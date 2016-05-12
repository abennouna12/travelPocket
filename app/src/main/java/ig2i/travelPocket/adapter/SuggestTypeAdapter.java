package ig2i.travelPocket.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ig2i.travelPocket.R;
import ig2i.travelPocket.model.SuggestionType;

/**
 * Created by aBennouna on 11/05/2016.
 */
public class SuggestTypeAdapter extends ArrayAdapter<SuggestionType>{
    List<SuggestionType> types = null;
    Context context;
    public SuggestTypeAdapter(Context context, List<SuggestionType> resource) {
        super(context,R.layout.suggestion_type,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.types = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.suggestion_type, parent, false);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.CheckboxSuggestType);
        cb.setText(types.get(position).getName());
        //name.setText(types.get(position).getName());
        if(types.get(position).getChecked())
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }
}


