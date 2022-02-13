package com.example.onlinebartertrading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Editor extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> detail = new ArrayList<>();
    ArrayList<String> value = new ArrayList<>();

    
    public Editor(ShowDetails context, ArrayList<String> name, ArrayList<String> detail, ArrayList<String> value) {
        super(context, R.layout.activity_listview, R.id.textview2, name);
        this.context = context;
        this.name = name;
        this.detail = detail;
        this.value = value;

    }

/**
*getView
*The parent is your custom adapter that you inflate a row into it. convertView is the GUI(view) of the row in the Position position in your adapter.
**/

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View view = convertView;
        NameSet nameSet;
        if(view != null)
        {
            nameSet = (NameSet) view.getTag();
        }else
        {

/**
*The LayoutInflater takes layout XML-files and creates different View-objects from its contents.
**/

            
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.activity_listview, parent, false);
            nameSet = new NameSet(view);
            view.setTag(nameSet);
        }

        nameSet.name.setText(name.get(position));
        nameSet.detail.setText(detail.get(position));
        nameSet.value.setText("$" + value.get(position));

        return view;
    }
}
