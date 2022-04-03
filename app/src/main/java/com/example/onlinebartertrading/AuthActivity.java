package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.telephony.SignalStrength;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Authentication activity
 * Implements LoginForm and SignupForm Fragments
 */
public class AuthActivity extends AppCompatActivity {

    TabLayout panes;

    /**
     * View setup
     * Implements TabLayout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        panes = findViewById(R.id.authTabs);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(panes, viewPager, (tab, position) -> {
            if(position == 0) {
                tab.setText("Sign Up");
            }
            else if(position == 1) {
                tab.setText("Log In");
            }
        }).attach();

        // Debug toast to check for Cloud Messaging errors
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "FCM token is missing", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                    }
                });
    }

    /**
     * Adapter to implements Fragments into Tab Layout
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position == 0) {
                return new SignupFormFragment();
            }
            else return new LoginFormFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


}