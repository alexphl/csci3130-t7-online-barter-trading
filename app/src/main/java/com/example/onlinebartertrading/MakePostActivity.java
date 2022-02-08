package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MakePostActivity extends AppCompatActivity implements View.OnClickListener {
    public static int maxTitleLength = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);
        Button postButton = findViewById(R.id.makePostButton);
        postButton.setOnClickListener(this);
    }

    protected void setStatusMessage(String message){
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message.trim());
    }

    protected String getTitleDesc(){
        EditText titleBox = findViewById(R.id.postTitle);
        return titleBox.getText().toString().trim();
    }

    protected Boolean validTitleDesc(String title){
        if (title.equals("")) {
            return false;
        }
        if (title.length()>50){
            return false;
        }
        return true;
    }




    @Override
    public void onClick(View view) {
        String title = getTitleDesc();
        String errorMessage = "";
        if (!validTitleDesc(title)){
            errorMessage = getResources().getString(R.string.INVALID_TITLE).trim();
        }

        setStatusMessage(errorMessage);
    }
}