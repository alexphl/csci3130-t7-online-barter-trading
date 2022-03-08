package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents the details activity
 * which appears when a user enters a product post
 **/
public class ShowDetails extends AppCompatActivity implements View.OnClickListener {

    ListView listGoods;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> detail = new ArrayList<>();
    ArrayList<String> value = new ArrayList<>();
    Button showButton;
    Editor editor;
    ArrayList<DataSnapshot> values;
    DatabaseReference reference;
    String searchKeyword = "chair";

    int numPosts = 0;

    /**
     * View setup and value listeners
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchKeyword = getSearchQuery(getIntent());
        //listview layout
        setContentView(R.layout.activity_listmain);
        listGoods = findViewById(R.id.listView);
        listGoods.addFooterView(getLayoutInflater().inflate(R.layout.footer_view, null));

        showButton = findViewById(R.id.btn);
        showButton.setOnClickListener(this);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                () -> {
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                    swipeRefreshLayout.setRefreshing(false);
                }
        );

        reference = FirebaseDatabase.getInstance().getReference().child("posts");

        Query query = reference.orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //get data from firebase
                Iterator<DataSnapshot> snapshots = datasnapshot.getChildren().iterator();
                ArrayList<DataSnapshot> list = new ArrayList<>();
                snapshots.forEachRemaining(list::add);
                values = applyFilters(list);
                extractPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: handle this exception
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem search = menu.findItem(R.id.search);

        MenuItem filter = menu.findItem(R.id.setting);

        filter.setOnMenuItemClickListener(item -> {
         Intent intent = new Intent(getBaseContext(), MakePostActivity.class);
//             intent.putExtra();
         startActivity(intent);
         return false;
        });
        filter.setVisible(false);

        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                filter.setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                filter.setVisible(false);
                return true;
            }

        });

        SearchView searchView =
                (SearchView) search.getActionView();
        searchView.setIconifiedByDefault(false);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    private String getSearchQuery(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println(query);
            return query.trim();
        }
        return "";
    }

    private ArrayList<DataSnapshot> applyFilters(ArrayList<DataSnapshot> data) {
        String local_area = "HRM";
        String category = "Furnishing";
        //The upper limit of the price
        int valueUpperLimit = 2000;
        //Upper limit of distance. This should be in meters
        int distance = 1000;
        //This is the current location, this would be taken from the intent from the activity that detects location.
        double current_lat = 44.637438120009094;
        double current_long = -63.57768822532626;
        LatLng current_position = new LatLng(current_lat, current_long);



        ArrayList<DataSnapshot> list = new ArrayList<>();
        for(DataSnapshot value : data) {
            double post_lat = Double.parseDouble(value.child("latitude").getValue().toString());
            double post_long = Double.parseDouble(value.child("longitude").getValue().toString());
            LatLng post_position = new LatLng(post_lat, post_long);

            double distance_between = SphericalUtil.computeDistanceBetween(current_position, post_position);
//            System.out.println(distance_between);
            //Refactor
//            if(value.child("category").getValue().toString().equals(category) &&  value.child("area").getValue().toString().equals(local_area) && distance_between <= distance && Integer.parseInt(value.child("value").getValue().toString()) <= valueUpperLimit)
            if ((value.child("title").getValue().toString().contains(searchKeyword) || value.child("desc").getValue().toString().contains(searchKeyword))) {
                list.add(value);
            }
        }
        return list;
    }


    private void extractPosts() {
        int batchCount = 0;
        for (int i = values.size() - numPosts -1; i >= 0; i--) {
            DataSnapshot snapshot = values.get(i);
            String tit = snapshot.child("title").getValue().toString();
            String de = snapshot.child("desc").getValue().toString();
            String val = snapshot.child("value").getValue().toString();


            if(!tit.isEmpty() && !de.isEmpty() && !val.isEmpty()) {
                batchCount++;
                name.add(tit);
                detail.add(de);
                value.add(val);
            }
            if(batchCount == 4) break;
        }

        numPosts += batchCount;

        if(batchCount < 4 || numPosts == values.size()) {
            showButton.setVisibility(View.GONE);
        }

        //Send to adapter and make data in each layout
        if(editor == null) {
            editor = new Editor(ShowDetails.this, name, detail, value);
        }
        else {
            editor.notifyDataSetChanged();
        }

        listGoods.setAdapter(editor);
    }

    @Override
    public void onClick(View view) {
        extractPosts();
    }

}
