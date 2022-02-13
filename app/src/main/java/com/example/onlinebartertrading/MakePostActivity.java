package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MakePostActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int maxTitleLength = 50;
    public static final int maxDescLength = 180;
    public static final int maxValue = 1000000;
    private DatabaseReference myDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Button postButton = findViewById(R.id.makePostButton);
        postButton.setOnClickListener(this);
        myDatabase = FirebaseDatabase.getInstance().getReference();
    }

    protected void setStatusMessage(String message){
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

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
        return Float.parseFloat(valueBox.getText().toString().trim());
    }

    protected Boolean validTitleDesc(String title){
        if (title.equals("")) {
            return false;
        }
        if (title.length()>maxTitleLength){
            return false;
        }
        return true;
    }

    protected Boolean isEmptyDesc(String desc){
        if (desc.equals("")){
            return true;
        }
        return false;
    }

    protected Boolean isValidDesc(String desc){
        if (desc.length()>maxDescLength){
            return false;
        }
        return true;
    }

    protected Boolean isValidValue(float value){
        if (value>maxValue || value<1){
            return false;
        }
        return true;
    }

    protected void switch2ShowDetail(String title, String desc, float value) {
        Intent intent = new Intent(MakePostActivity.this, ShowDetails.class);
        intent.putExtra("title",title);
        intent.putExtra("desc",desc);
        intent.putExtra("value",value);
        startActivity(intent);
    }



    @Override
    public void onClick(View view) {
        String title = getTitleDesc();
        String desc = getDesc();
        float value = getValue();
        String errorMessage = "";
        if (!validTitleDesc(title)){
            errorMessage = getResources().getString(R.string.INVALID_TITLE).trim();
        }
        if (isEmptyDesc(desc)){
            errorMessage = getResources().getString(R.string.EMPTY_DESC).trim();
        }
        if (!isValidDesc(desc)){
            errorMessage = getResources().getString(R.string.LONG_DESC).trim();
        }
        if (!isValidValue(value)){
            errorMessage = getResources().getString((R.string.INVALID_VALUE));
        }

        setStatusMessage(errorMessage);
        if (errorMessage.equals("")){
            PostDetails newPost = new PostDetails(title,desc,value);
            myDatabase.child("posts").child("Details"+title).setValue(newPost);
        }
        switch2ShowDetail(title,desc,value);

    }
}