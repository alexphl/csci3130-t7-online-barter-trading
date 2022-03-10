package com.example.onlinebartertrading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Post editing interface
 **/
public class Editor extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> detail;
    ArrayList<String> value;

    /**
     * @param context
     * @param name      User provided name
     * @param detail    User provided details
     * @param value     User provided item valuation
     */
    public Editor(ShowDetailsActivity context, ArrayList<String> name, ArrayList<String> detail, ArrayList<String> value) {
        super(context, R.layout.activity_listview, R.id.itemDetail, name);
        this.context = context;
        this.name = name;
        this.detail = detail;
        this.value = value;
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
            view = layoutInflater.inflate(R.layout.activity_listview, parent, false);
            nameSet = new HashMap<>();

            nameSet.put("name", view.findViewById(R.id.itemName));
            nameSet.put("value", view.findViewById(R.id.itemValue));
            nameSet.put("detail", view.findViewById(R.id.itemDetail));

            view.setTag(nameSet);
        }

        Objects.requireNonNull(nameSet.get("name")).setText(name.get(position));
        Objects.requireNonNull(nameSet.get("detail")).setText(detail.get(position));
        Objects.requireNonNull(nameSet.get("value")).setText("$" + value.get(position));

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
