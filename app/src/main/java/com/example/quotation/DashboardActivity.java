package com.example.quotation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fragments.AboutFragment;
import com.example.fragments.FavouriteFragment;
import com.example.fragments.QuotationFragment;

public class DashboardActivity extends AppCompatActivity {

    Class<? extends Fragment> fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button buttonGetQuotations = findViewById(R.id.getQuotation);
        Button buttonFavouriteQuotations = findViewById(R.id.favouriteQuotations);
        Button buttonSettings = findViewById(R.id.settings);
        Button buttonAbout = findViewById(R.id.about);

        View.OnClickListener x = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (buttonGetQuotations.isPressed()) {
                    /*Intent intent = new Intent(DashboardActivity.this, QuotationFragment.class);
                    startActivity(intent);*/
                    fragment = AboutFragment.class;
                    getSupportActionBar().setTitle(R.string.bGetQuotations);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvMain, fragment, null);
                }
                if (buttonFavouriteQuotations.isPressed()) {
                    /*Intent intent = new Intent(DashboardActivity.this, FavouriteFragment.class);
                    startActivity(intent);*/
                    fragment = AboutFragment.class;
                    getSupportActionBar().setTitle(R.string.bFavourite);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvMain, fragment, null);
                }
                if (buttonSettings.isPressed()) {
                    /*Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                    startActivity(intent);*/
                    fragment = AboutFragment.class;
                    getSupportActionBar().setTitle(R.string.bSettings);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fcvMain, fragment, null);
                }
                if (buttonAbout.isPressed()) {
                    //Intent intent = new Intent(DashboardActivity.this, AboutFragment.class);
                    //startActivity(intent);
                   fragment = AboutFragment.class;
                   getSupportActionBar().setTitle(R.string.bAbout);
                   getSupportFragmentManager()
                           .beginTransaction()
                           .setReorderingAllowed(true)
                           .replace(R.id.fcvMain, fragment, null)
                   .commit();
                }
            }

        };

        buttonGetQuotations.setOnClickListener(x);
        buttonFavouriteQuotations.setOnClickListener(x);
        buttonSettings.setOnClickListener(x);
        buttonAbout.setOnClickListener(x);
    }
}