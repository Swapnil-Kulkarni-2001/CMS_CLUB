package com.example.cms_club_ver_1;

import android.content.Context;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static Context MainActivity_context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity_context = getApplicationContext();
        bottomNavigationView = findViewById(R.id.botttomNavigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new fragment_home()).commit();

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            switch (item.getItemId())
            {
                case R.id.home:
                    fragment = new fragment_home();
                    break;
                case R.id.members:
                    fragment = new fragments_members();
                    break;
                case R.id.events:
                    fragment = new fragment_event();
                    break;
                case R.id.services:
                    fragment = new fragment_service();
                    break;
                case R.id.more:
                    fragment = new fragment_more();
                    break;
            }

            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
            return true;
        });
    }
}