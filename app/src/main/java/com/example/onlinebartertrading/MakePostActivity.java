package com.example.onlinebartertrading;


import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.example.onlinebartertrading.entities.Post;
import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.LocationProvider;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the Activity a user sees when making an item posts
 */
public class MakePostActivity extends BaseActivity implements View.OnClickListener {

    public static final int maxTitleLength = 50;
    public static final int maxDescLength = 180;
    public static final int maxValue = 1000000;
    private static final String area = "HRM";
    private User user;
    private DatabaseReference myDatabase;
    private RequestQueue requestQueue;

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    /**
     * Preliminary setup
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Button postButton = findViewById(R.id.makePostButton);

        user = (User) getIntent().getSerializableExtra("user");
        user.setLocationProvider(new LocationProvider(this));

        requestQueue = Volley.newRequestQueue(this);

        postButton.setOnClickListener(this);
        myDatabase = FirebaseDatabase.getInstance().getReference();
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
        ChipGroup pref = findViewById(R.id.allChips);
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
     */
protected void switch2ShowDetail() {
        Intent intent = new Intent(MakePostActivity.this, PostListActivity.class);
        intent.putExtra("user", user);
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
        String category = getCategory();
        float value = getValue();

        String errorMessage = "";

        if (user.getLocation().latitude == 0) errorMessage = "Location fetch failed";
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
            Post newPost = new Post(user.getEmail(), title, desc, value, category, user.getLocation());
            myDatabase.child("posts").child(time).setValue(newPost);

            HashMap<String, Object> map = new HashMap<>();
            map.put("post_title", title);
            map.put("post_value", value);
            map.put("status", "incomplete");
            myDatabase.child("users").child(UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString()).child("history_provider").child(time).setValue(map);

            sendNotification(title, desc, category);

            switch2ShowDetail();
        }

    }

    /**
     * Sends an FCM notification to users who subscribed to the post's category
     */
    private void sendNotification(String title, String desc, String cat) {
        try {
            final JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", "New Items Added!");
            notificationJSONBody.put("body", "New goods have arrived, take a look!");

            final JSONObject dataJSONBody = new JSONObject();
            dataJSONBody.put("title", title);
            dataJSONBody.put("description", desc);
            dataJSONBody.put("category", cat);

            String topicPath = "/topic/" + cat.replaceAll(" ", "_").toLowerCase();

            final JSONObject pushNotificationJSONBody = new JSONObject();
            pushNotificationJSONBody.put("to", topicPath);
            pushNotificationJSONBody.put("notification", notificationJSONBody);
            pushNotificationJSONBody.put("data", dataJSONBody);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    "https://fcm.googleapis.com/fcm/send",
                    pushNotificationJSONBody,
                    response ->
                            Toast.makeText(MakePostActivity.this,
                                    "Your item has been posted!",
                                    Toast.LENGTH_SHORT).show(),
                    Throwable::printStackTrace) {
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("content-type", "application/json");
                    headers.put("authorization", "key=" + FirebaseConstants.FIREBASE_SERVER_KEY);
                    return headers;
                }
            };

            requestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}


