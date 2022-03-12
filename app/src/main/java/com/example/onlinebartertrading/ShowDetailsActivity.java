package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.widget.ListView;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.chip.Chip;
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
public class ShowDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listGoods;
    /*
    These lists hold the posts that are needed to be displayed. These are passed to Editor.java to add the the ArrayAdaptor.
     */
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> detail = new ArrayList<>();
    ArrayList<String> value = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> email = new ArrayList<>();
    ArrayList<String> distance = new ArrayList<>();
    Button showButton;
    Editor editor;
    Chip chip;
    LatLng position;
    /*
    This holds the posts after applying filter and keyword search.
     */
    ArrayList<DataSnapshot> values;
    DatabaseReference reference;
    String searchKeyword = "";
    Preferences preferences;
    String userEmail;

    // Necessary for location provider
    private LocationProvider locationProvider;
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
        listGoods.addHeaderView(getLayoutInflater().inflate(R.layout.header_view, null));

        showButton = findViewById(R.id.btn);
        showButton.setOnClickListener(this);

        // We call this here as it takes a second to fetch user location
        double[] lastLocation = getIntent().getDoubleArrayExtra("lastLocation");
        locationProvider = new LocationProvider(this, lastLocation);

        //Get user email from intent
        userEmail = getIntent().getStringExtra("userEmail");

        //Swipe down to refresh lets the user automatically show any new posts that are made by other users.
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

        double[] userLocation = locationProvider.getLocationUpdate();
        position = new LatLng(userLocation[0], userLocation[1]);

        reference = FirebaseDatabase.getInstance().getReference().child("posts");

        Query query = reference.orderByKey();

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //get data from firebase
                Iterator<DataSnapshot> snapshots = datasnapshot.getChildren().iterator();
                ArrayList<DataSnapshot> list = new ArrayList<>();
                //Get all values from iterator
                snapshots.forEachRemaining(list::add);
                preferences = (Preferences) getIntent().getSerializableExtra("preferences");
                values = applyFilters(list);
                extractPosts();
                if(preferences == null) {
                    chip.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: handle this exception
            }
        });

        chip = findViewById(R.id.filterChip);
        chip.setOnCloseIconClickListener(view -> {
            Intent intent = getIntent();
            String param = null;
            intent.putExtra("preferences", param);
            startActivity(getIntent());
        });
    }

    /**
     * Specify the settings of the options menu
     * @param menu - the object for the options menu.
     * @return a boolean representing successful creation of the options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem search = menu.findItem(R.id.search);

        MenuItem filter = menu.findItem(R.id.setting);
        //Behaviour for when filter button is clicked. The user will be taken to the preferences activity.
        filter.setOnMenuItemClickListener(item -> {
         Intent intent = new Intent(getBaseContext(), PreferenceActivity.class);
         intent.putExtra("lastLocation", locationProvider.getLocationUpdate());
         intent.putExtra("userEmail", userEmail);
         startActivity(intent);
         return false;
        });

        SearchView searchView =
                (SearchView) search.getActionView();
        searchView.setIconifiedByDefault(false);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    /**
     * Retrieve search keyword from intent
     * @param intent
     * @return the search keyword entered by the user.
     */
    private String getSearchQuery(Intent intent) {
        //Search query is added to the Intent with the ACTION_SEARCH action on Searchable.
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            return query.trim();
        }
        //Defaults to an empty string which will display all posts.
        return "";
    }

    /**
     * Apply filters to modify the values ArrayList that is used to show results in ShowDetails activity.
     * @param data containing all the results of the query
     * @return ArrayList containing only the posts that match the filters
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ArrayList<DataSnapshot> applyFilters(ArrayList<DataSnapshot> data) {
        ArrayList<String> categories = new ArrayList<>();
        int valueUpperLimit =  1000000;
        int valueLowerLimit = 0;
        int distance = 1000;

        if(preferences != null) {
            categories = preferences.getCategories();
            //The upper limit of the price
            valueUpperLimit = preferences.getMaxValue();
            valueLowerLimit = preferences.getMinValue();
            distance = preferences.getDistance() * 1000;

            //Upper limit of distance. This should be in meters
        }
        //This is the current location, this would be taken from the intent from the activity that detects location.
        LatLng current_position = position;

        ArrayList<DataSnapshot> list = new ArrayList<>();
        for(DataSnapshot value : data) {
            double post_lat = Double.parseDouble(value.child("latitude").getValue().toString());
            double post_long = Double.parseDouble(value.child("longitude").getValue().toString());
            LatLng post_position = new LatLng(post_lat, post_long);

            double distance_between = SphericalUtil.computeDistanceBetween(current_position, post_position);
//            System.out.println(distance_between);
            //Refactor
            if ((value.child("title").getValue().toString().contains(searchKeyword) || value.child("desc").getValue().toString().contains(searchKeyword))) {
                if(preferences == null || ((categories.isEmpty() || categories.contains(value.child("category").getValue().toString()))  && distance_between <= distance && valueLowerLimit <= Integer.parseInt(value.child("value").getValue().toString()) && Integer.parseInt(value.child("value").getValue().toString()) <= valueUpperLimit)) {
                    list.add(value);
                }
            }
        }
        return list;
    }

    /**
     * Method that displays search results in a multiple of 4 depending on the number of clicks of Show More button.
     * Modifies numPosts which keeps track of the total posts currently on page.
     */
    private void extractPosts() {
        int batchCount = 0;
        //Start before the postings already on screen.
        for (int i = values.size() - numPosts -1; i >= 0; i--) {
            DataSnapshot snapshot = values.get(i);
            String tit = snapshot.child("title").getValue().toString();
            String de = snapshot.child("desc").getValue().toString();
            String val = snapshot.child("value").getValue().toString();
            String cat = snapshot.child("category").getValue().toString();
            String posterEmail = snapshot.child("posterEmail").getValue().toString();
            LatLng itemPosition = new LatLng(Double.parseDouble(snapshot.child("latitude").getValue().toString()), Double.parseDouble(snapshot.child("longitude").getValue().toString()));
            LatLng currentPosition = position;
            int distance = (int) (SphericalUtil.computeDistanceBetween(itemPosition, currentPosition));
            String distVal = "<"+(distance/1000+10)/10 *10;


            if(!tit.isEmpty() && !de.isEmpty() && !val.isEmpty()) {
                batchCount++;
                name.add(tit);
                detail.add(de);
                value.add(val);
                category.add(cat);
                email.add(posterEmail);
                this.distance.add(distVal);
            }
            if(batchCount == 5) break;
        }

        numPosts += batchCount;
        //Disable search button if we did not get 4 posts or we get all of the posts.
        if(batchCount < 5 || numPosts == values.size()) {
            showButton.setVisibility(View.GONE);
        }

        //Send to adapter and make data in each layout
        if(editor == null) {
            editor = new Editor(ShowDetailsActivity.this, name, detail, value, category, distance, email);
        }
        else {
            editor.notifyDataSetChanged();
        }

        listGoods.setAdapter(editor);
    }

    /**
     * Behavior when the show more button is clicked.
     * @param view
     */
    @Override
    public void onClick(View view) {
        extractPosts();
    }

}
