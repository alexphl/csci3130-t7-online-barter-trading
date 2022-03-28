package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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

        MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getBaseContext(), AuthActivity.class);
            startActivity(intent);
            return false;
        });

        return true;
    }
}