package com.example.quotation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.fragments.AboutFragment;
import com.example.fragments.FavouriteFragment;
import com.example.fragments.QuotationFragment;
import com.example.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {

    Class<? extends Fragment> fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ((BottomNavigationView) findViewById(R.id.btNavigarionView)).setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if (item.getItemId() == R.id.funcGetQuotations) {
                            fragment = QuotationFragment.class;
                            getSupportActionBar().setTitle(R.string.bGetQuotations);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fcvMain, fragment, savedInstanceState)
                                    .commit();
                        }
                        if (item.getItemId() == R.id.funcFavouriteQuotations) {
                            fragment = FavouriteFragment.class;
                            getSupportActionBar().setTitle(R.string.bFavourite);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fcvMain, fragment, savedInstanceState)
                                    .commit();
                        }
                        if (item.getItemId() == R.id.funcSettings) {
                            fragment = SettingsFragment.class;
                            getSupportActionBar().setTitle(R.string.bSettings);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fcvMain, fragment, savedInstanceState)
                                    .commit();
                        }
                        if (item.getItemId() == R.id.funcAbout) {
                            fragment = AboutFragment.class;
                            getSupportActionBar().setTitle(R.string.bAbout);
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .setReorderingAllowed(true)
                                    .replace(R.id.fcvMain, fragment, savedInstanceState)
                                    .commit();
                        }
                        return true;
                    }
                }
        );

        if (savedInstanceState == null) {
            fragment = QuotationFragment.class;
            getSupportActionBar().setTitle(R.string.bGetQuotations);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fcvMain, fragment, null)
                    .commit();
        } else {

        }
    }
}