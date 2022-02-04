package com.example.quotation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

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
                    Intent intent = new Intent(DashboardActivity.this, QuotationActivity.class);
                    startActivity(intent);
                }
                if (buttonFavouriteQuotations.isPressed()) {
                    Intent intent = new Intent(DashboardActivity.this, FavouriteActivity.class);
                    startActivity(intent);
                }
                if (buttonSettings.isPressed()) {
                    Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                    startActivity(intent);
                }
                if (buttonAbout.isPressed()) {
                    Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
            }

        };

        buttonGetQuotations.setOnClickListener(x);
        buttonFavouriteQuotations.setOnClickListener(x);
        buttonSettings.setOnClickListener(x);
        buttonAbout.setOnClickListener(x);
    }
}