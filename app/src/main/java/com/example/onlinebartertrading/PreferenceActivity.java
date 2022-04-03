package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.example.onlinebartertrading.entities.Preferences;
import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.LocationProvider;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * This class provides a way for users to select their preferences
 */
public class PreferenceActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseReference userRef;
    User user;
    public static ArrayList<Integer> distanceChips;

    /**
     * Sets the new view up
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        user = (User) getIntent().getSerializableExtra("user");
        user.setLocationProvider(new LocationProvider(this));

        setPreferences();

        Button enterButton = findViewById(R.id.preferenceButton);
        enterButton.setOnClickListener(this);
        //save id of all distance chips
        distanceChips = new ArrayList<>();
        distanceChips.add(R.id.tenDist);
        distanceChips.add(R.id.twentyFiveDist);
        distanceChips.add(R.id.fiftyDist);
        distanceChips.add(R.id.hundredDist);
        distanceChips.add(R.id.twoHundredDist);
        distanceChips.add(R.id.twoHundredPlusDist);

    }

    protected void initializeUserDBRef() {
        DatabaseReference dbRef = FirebaseDatabase
                .getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference();

        String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
        userRef = dbRef.child(FirebaseConstants.USERS_COLLECTION).child(uuid);
    }

    /**
     * Sets the preferences of a user if they are stored in database
     */
    protected void setPreferences() {

        initializeUserDBRef();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String message;
                if (snapshot.hasChild("preferences")) {

                    Preferences preferences = snapshot
                            .child("preferences")
                            .getValue(Preferences.class);

                    setTags(preferences.getTags());
                    setMinValue(preferences.getMinValue());
                    setMaxValue(preferences.getMaxValue());
                    setDistance(preferences.getDistance());

                    message = "Preferences loaded";
                }
                else {
                    message = "No preferences found for user";
                }
                Toast.makeText(PreferenceActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR: " + error.getMessage());
            }
        });
    }

    /**
     * Method to check tags saved from database in UI
     * @param dbTags used to check which tags were stored in DB
     */
    protected void setTags(List<Integer> dbTags) {
        if (dbTags == null) {
            return;
        }

        for (Integer id: dbTags) {
            Chip tag = findViewById(id);
            if (tag != null) tag.setChecked(true);
            else System.out.println("NULL TAG ID: " + id);
        }
    }

    /**
     * sets minimum value EditText to whatever was stored in DB
     * @param minValue to set
     */
    protected void setMinValue(int minValue) {
        EditText minTextBox = findViewById(R.id.minValue);
        minTextBox.setText(String.format(Locale.ENGLISH, "%d", minValue));
    }

    /**
     * sets maximum value EditText to whatever was stored in DB
     * @param maxValue to set
     */
    protected void setMaxValue(int maxValue) {
        EditText maxTextBox = findViewById(R.id.maxValue);
        maxTextBox.setText(String.format(Locale.ENGLISH, "%d", maxValue));
    }

    /**
     * Selects the correct distance chip based on what is stored in DB
     * @param distance to check
     */
    protected void setDistance(int distance) {
        Chip distChip = findViewById(distanceChips.get(0));
        boolean checkedAChip = false;
        //compare distance chips with distance provided
        for (int i =0 ; i<distanceChips.size();i++){
            distChip = findViewById(distanceChips.get(i));
            if (distChip.getTag().equals(String.valueOf(distance))){
                distChip.setChecked(true);
                checkedAChip = true;
            }
        }
        if (!checkedAChip){
            distChip.setChecked(true);
        }
    }

    /**
     * Sets an error message if the user enters incorrect details
     * @param message
     */
    protected void setStatusMessage(String message){
        TextView statusLabel = findViewById(R.id.prefStatusLabel);
        statusLabel.setText(message.trim());
    }

    /**
     * returns the preferences selected
     * @return
     */
    protected List<Integer> getPreferences(){
        ChipGroup pref = findViewById(R.id.allChips);
        return pref.getCheckedChipIds();
    }

    /**
     * Gets the minimum value entered by the user
     * @return
     */
    protected int getMinValue(){
        EditText minTextBox = findViewById(R.id.minValue);
        int minValue;
        try {
            minValue = Integer.parseInt(minTextBox.getText().toString().trim());
        }
        catch (Exception e){
            //invalid integer so return error value
            minValue = -1;
        }
        return minValue;
    }

    /**
     * Gets the maximum value entered by the user
     * @return
     */
    protected int getMaxValue(){
        EditText minTextBox = findViewById(R.id.maxValue);
        int maxValue;
        try {
            maxValue = Integer.parseInt(minTextBox.getText().toString().trim());
        }
        catch (Exception e){
            //invalid integer so return error value
            maxValue = -2;
        }
        return maxValue;
    }

    /**
     * Gets the distance the user selected from the chips
     * @return
     */
    protected int getDistance(){
        ChipGroup pref = findViewById(R.id.distanceChips);
        int checkedChip = pref.getCheckedChipId();
        Chip selectedChip = findViewById(checkedChip);
        return Integer.parseInt(selectedChip.getTag().toString());
    }

    /**
     * checked if minimum value is not too small or too large
     * @param value
     * @return
     */
    protected boolean isValidMinValue(int value){
        return value >= 0 && value < MakePostActivity.maxValue;
    }

    /**
     * Checks if maximum is not too small or too large
     * @param value
     * @return
     */
    protected boolean isValidMaxValue(int value){
        return value >= 1 && value < MakePostActivity.maxValue;
    }

    /**
     * Checks the minimum is less than the maximum
     * @param min
     * @param max
     * @return
     */
    protected boolean minIsLessThanMax(int min, int max){
        return min <= max;
    }

    /**
     * When button is pressed, preferences are saved if they are valid or an error message is
     * displayed
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        List<Integer> selectedTags = getPreferences();
        int minValue = getMinValue();
        int maxValue = getMaxValue();
        int maxDistance = getDistance();
        String errorMessage = "";

        if (!isValidMinValue(minValue)){
            errorMessage =  getResources().getString(R.string.EMPTY_FIELD);
        }

        else if (!isValidMaxValue(maxValue)){
            errorMessage = getResources().getString(R.string.EMPTY_FIELD);
        }

        else if (!minIsLessThanMax(minValue,maxValue)){
            errorMessage = getResources().getString(R.string.MIN_LESS_MAX);
        }

        setStatusMessage(errorMessage);

        if (errorMessage.equals("")){
            // Saves preferences to DB for specific user
            Map<String, Object> preferences = new HashMap<>();
            Preferences userPref =
                    new Preferences(selectedTags, minValue, maxValue, maxDistance);
            preferences.put("preferences", userPref);

            userPref.setCategories((ArrayList<String>) userPref.getTags().stream().map(n -> ((Chip)findViewById(n)).getText().toString()).collect(Collectors.toList()));

            user.setPreferences(userPref);

            userRef.updateChildren(preferences);

            // switch to new activity
            Intent intent = new Intent(getBaseContext(), PostListActivity.class);
            intent.putExtra("user", user);
            FCMService.setUser(user);
            startActivity(intent);

        }
    }
}