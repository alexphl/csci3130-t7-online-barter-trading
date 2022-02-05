package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class makePostActivity extends AppCompatActivity implements View.OnClickListener {
    public static int maxTitleLength = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Button postButton = findViewById(R.id.makePostButton);
        postButton.setOnClickListener(this);
    }

    protected String getTitleDesc(){

        return null;
    }

    protected Boolean validTitleDesc(String title){

        return false;
    }




    @Override
    public void onClick(View view) {

    }
}