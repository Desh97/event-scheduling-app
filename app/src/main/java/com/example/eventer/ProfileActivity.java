package com.example.eventer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class ProfileActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment = null;


                    switch (menuItem.getItemId()){

                        case R.id.nav_home:
                            fragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            fragment = new SearchFragment();
                            break;
                        case R.id.nav_up:
                            fragment = new UploadFragmnet();
                            break;
                        case R.id.nav_fav:
                            fragment = new FavouriteFragment();
                            break;
                        case R.id.nav_settings:
                            fragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

                        return true;
                }
            };



    @Override
    public void onBackPressed() {

    }
}
