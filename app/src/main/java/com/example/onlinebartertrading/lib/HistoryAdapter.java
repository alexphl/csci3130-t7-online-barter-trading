package com.example.onlinebartertrading.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.onlinebartertrading.ProfileActivity;
import com.example.onlinebartertrading.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Post editing interface
 **/
public class HistoryAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> value;
    ArrayList<String> status;

    /**
     * @param context
     * @param name      User provided name
     * @param value     User provided item valuation
     */
    public HistoryAdapter(ProfileActivity context, ArrayList<String> name, ArrayList<String> value, ArrayList<String> status) {
        super(context, R.layout.activity_historypost, R.id.itemName, name);
        this.context = context;
        this.name = name;
        this.value = value;
        this.status = status;
    }

    /**
     * The parent is your custom adapter that you inflate a row into it.
     * convertView is the GUI(view) of the row in the Position position in your adapter.
     **/
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View view = convertView;
        HashMap<String, TextView> nameSet;
        if(view != null) {
            nameSet = (HashMap<String, TextView>) view.getTag();
        } else {
            //The LayoutInflater takes layout XML-files and creates different View-objects from its contents.
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.activity_historypost, parent, false);
            nameSet = new HashMap<>();

            // Set up hash map
            nameSet.put("name", view.findViewById(R.id.itemName));
            nameSet.put("value", view.findViewById(R.id.itemValue));
            nameSet.put("status",view.findViewById(R.id.statusOfTrade));


            String title = name.get(position);
            String price = "$" + value.get(position);
            String stat = status.get(position);

            Objects.requireNonNull(nameSet.get("name")).setText(title);
            Objects.requireNonNull(nameSet.get("value")).setText(price);
            Objects.requireNonNull(nameSet.get("status")).setText(stat);
        }

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
