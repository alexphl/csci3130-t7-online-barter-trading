package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class PreferenceActivity extends AppCompatActivity implements View.OnClickListener {
    //km
    public static final int MAX_DISTANCE = 1000;
    public static String areaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        //get area from intent
        areaText = "stub area";
        //set the area
        TextView localAreaText = (TextView) findViewById(R.id.localArea);
        localAreaText.setText(areaText);
        Chip areaChip = (Chip) findViewById(R.id.localTrue);
        areaChip.setText(areaText);

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

    protected int getDistance(){
        ChipGroup pref = findViewById(R.id.allChips);
        int checkedChip = pref.getCheckedChipId();
        int maxDistance = MAX_DISTANCE;
        if (checkedChip == R.id.tenDist){
            maxDistance = 10;
        }
        else if (checkedChip == R.id.twentyFiveDist){
            maxDistance = 25;
        }
        else if (checkedChip == R.id.fiftyDist){
            maxDistance = 50;
        }
        else if (checkedChip == R.id.hundredDist){
            maxDistance = 100;
        }
        else if (checkedChip == R.id.twoHundredDist){
            maxDistance = 200;
        }
        return maxDistance;
    }

    protected boolean isValidMinValue(int value){
        if (value>=0 && value<MakePostActivity.maxValue){
            return true;
        }
        return false;
    }

    protected boolean isValidMaxValue(int value){
        if (value>=1 && value<MakePostActivity.maxValue){
            return true;
        }
        return false;
    }

    protected boolean minIsLessThanMax(int min, int max){
        if (min<= max){
            return true;
        }
        return false;
    }



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
            PreferenceClass userPref = new PreferenceClass();
            userPref.setDistance(maxDistance);
            userPref.setMaxValue(maxValue);
            userPref.setMinValue(minValue);
            userPref.setTags(selectedTags);
            userPref.setLocalArea(areaText);

            //switch to new activity
        }
    }
}