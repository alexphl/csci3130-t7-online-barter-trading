package com.example.onlinebartertrading;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.onlinebartertrading.entities.User;

public class BaseActivity extends AppCompatActivity {

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        user = (User) getIntent().getSerializableExtra("user");
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

        MenuItem profile = menu.findItem(R.id.profile);
        profile.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            return false;
        });

        MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(getBaseContext(), AuthActivity.class);
            startActivity(intent);
            return false;
        });

        return true;
    }
}