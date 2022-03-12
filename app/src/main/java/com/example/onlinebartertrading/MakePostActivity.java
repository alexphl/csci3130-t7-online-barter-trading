package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Represents the Activity a user sees when making an item posts
 */
public class MakePostActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int maxTitleLength = 50;
    public static final int maxDescLength = 180;
    public static final int maxValue = 1000000;
    private static final String area = "HRM";
    private static double[] location;
    private String userEmail;
    private DatabaseReference myDatabase;
    private LocationProvider locationProvider;

    /**
     * Preliminary setup
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Button postButton = findViewById(R.id.makePostButton);
        userEmail = getIntent().getStringExtra("userID");
        postButton.setOnClickListener(this);
        myDatabase = FirebaseDatabase.getInstance().getReference();

        //get location
        locationProvider = new LocationProvider(this);
        location = locationProvider.getLocationUpdate();
    }

    /**
     * Sets status label text
     * @param message string to set status to
     */
    protected void setStatusMessage(String message){
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    /**
     * Below are the getter methods
     * Don't warrant their own descriptions
     */
    protected String getTitleDesc(){
        EditText titleBox = findViewById(R.id.postTitle);
        return titleBox.getText().toString().trim();
    }

    protected  String getDesc(){
        EditText descBox = findViewById(R.id.postDesc);
        return descBox.getText().toString().trim();
    }

    protected float getValue(){
        EditText valueBox = findViewById(R.id.postValue);
        return valueBox.getText().toString().isEmpty() ? -1 : Float.parseFloat(valueBox.getText().toString().trim());
    }

    protected String getCategory(){
        ChipGroup pref = findViewById(R.id.distanceChips);
        int checkedChip = pref.getCheckedChipId();
        Chip checked = findViewById(checkedChip);
        return checked.getText().toString();
    }

    /**
     * Below are the validator methods
     * Don't warrant their own descriptions
     */
    protected Boolean validTitleDesc(String title){
        return !title.equals("") && title.length() <= maxTitleLength;
    }
    protected Boolean isEmptyDesc(String desc) {return desc.equals("");}
    protected Boolean isValidDesc(String desc) {return desc.length() <= maxDescLength;}
    protected Boolean isValidValue(float value) {return !(value > maxValue) && !(value < 1);}

    /**
     * Switches to ShowDetail Activity.
     * Creates an Intent with the following params:
     * @param desc      user provided description
     * @param title     user provided title
     * @param value     user provided value
     */
protected void switch2ShowDetail(String title, String desc, float value,String category) {
        Intent intent = new Intent(MakePostActivity.this, ShowDetailsActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("desc",desc);
        intent.putExtra("value",value);
        intent.putExtra("category", category);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }

    /**
     * "Post" button processor
     * Validates the description and value fields
     */
    @Override
    public void onClick(View view) {
        String title = getTitleDesc();
        String desc = getDesc();
        //String category = getCategory();
        String category = "Furnishing";
        float value = getValue();

        String errorMessage = "";

        if (location[0] == 0) errorMessage = "Location fetch failed";
        if (!validTitleDesc(title)){
            errorMessage = getResources().getString(R.string.INVALID_TITLE).trim();
        }
        if (isEmptyDesc(desc)){
            errorMessage = getResources().getString(R.string.EMPTY_DESC).trim();
        }
        if (!isValidDesc(desc)){
            errorMessage = getResources().getString(R.string.LONG_DESC).trim();
        }
        if (!isValidValue(value) || value == -1){
            errorMessage = getResources().getString((R.string.INVALID_VALUE));
        }

        setStatusMessage(errorMessage);
        if (errorMessage.equals("")){
            String time = Long.toString(System.currentTimeMillis());
            Post newPost = new Post(userEmail, title, desc, value, category, location);
            myDatabase.child("posts").child(time).setValue(newPost);
            switch2ShowDetail(title,desc,value,category);
        }

    }
}


