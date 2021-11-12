package com.esprit.socialapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NavigationActivity extends AppCompatActivity {
BottomNavigationView bottomNavigationView ;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String KEY_ID ="id";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_NAME ="name";
    private static final String KEY_LAST ="lastName";
    private static final String KEY_PASS ="password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commit();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null ;
                switch (item.getItemId()){

                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_fav:
                        fragment = new FavFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.nav_search:
                        fragment = new SearchFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragment).commit();
                return true;
            }
        });
    }
}