package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class PreferenceActivity extends AppCompatActivity implements View.OnClickListener {
    //km
    public static final int MAX_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        Button enterButton = findViewById(R.id.preferenceButton);
        enterButton.setOnClickListener(this);

    }

    protected void setStatusMessage(String message){
        TextView statusLabel = findViewById(R.id.prefStatusLabel);
        statusLabel.setText(message.trim());
    }

    protected List<Integer> getPreferences(){
        ChipGroup pref = findViewById(R.id.allChips);
        List<Integer> checkedChips = pref.getCheckedChipIds();
        return checkedChips;
    }

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



    protected boolean isValidMinValue(int value){
        if (value>=0 && value<MakePostActivity.maxValue){
            return true;
        }
        return false;
    }



    @Override
    public void onClick(View view) {
        List<Integer> selectedTags = getPreferences();
        int minValue = getMinValue();
        String errorMessage = "";

        if (!isValidMinValue(minValue)){
            errorMessage =  getResources().getString(R.string.EMPTY_FIELD);
        }

        setStatusMessage(errorMessage);
    }
}