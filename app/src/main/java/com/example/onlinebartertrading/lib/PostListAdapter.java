package com.example.onlinebartertrading.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.onlinebartertrading.PostListActivity;
import com.example.onlinebartertrading.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Post editing interface
 **/
public class PostListAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> detail;
    ArrayList<String> value;
    ArrayList<String> category;
    ArrayList<String> distance;
    ArrayList<String> email;
    ArrayList<String> post;

    /**
     * @param context
     * @param name      User provided name
     * @param detail    User provided details
     * @param value     User provided item valuation
     * @param category  User provided category
     */
    public PostListAdapter(PostListActivity context, ArrayList<String> post, ArrayList<String> name, ArrayList<String> detail, ArrayList<String> value, ArrayList<String> category, ArrayList<String> distance, ArrayList<String> email) {
        super(context, R.layout.activity_listview, R.id.itemDetail, name);
        this.context = context;
        this.post = post;
        this.name = name;
        this.detail = detail;
        this.value = value;
        this.category = category;
        this.distance = distance;
        this.email = email;
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

            // Set up hash map
            nameSet.put("name", view.findViewById(R.id.itemName));
            nameSet.put("detail", view.findViewById(R.id.itemDetail));
            nameSet.put("value", view.findViewById(R.id.itemValue));
            nameSet.put("email", view.findViewById(R.id.itemPoster));
            nameSet.put("distance", view.findViewById(R.id.itemDistance));
            nameSet.put("category", view.findViewById(R.id.itemCategory));

            view.setTag(R.string.nameset, nameSet);
            view.setTag(R.string.postid, post.get(position));
        }

        String title = name.get(position);
        String description = detail.get(position);
        String price = "$" + value.get(position);
        String cat = "Category: " + category.get(position);
        String dist = distance.get(position) + "km";
        System.out.println(dist);
        String mail = "Posted By: " + email.get(position);
        Objects.requireNonNull(nameSet.get("name")).setText(title);
        Objects.requireNonNull(nameSet.get("detail")).setText(description);
        Objects.requireNonNull(nameSet.get("value")).setText(price);
        Objects.requireNonNull(nameSet.get("email")).setText(mail);
        Objects.requireNonNull(nameSet.get("distance")).setText(dist);
        Objects.requireNonNull(nameSet.get("category")).setText(cat);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
