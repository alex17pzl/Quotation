package com.example.quotation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        Button buttonFavourite = findViewById(R.id.bAuhorInfo);

        View.OnClickListener x = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + "Albert Einstein"));
                //intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Albert_Einstein"));

                // Get the list of Activities able to manage that Intent
                List<ResolveInfo> activities =
                        getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (activities.size() > 0) {
                    startActivity(intent);
                }
            }
        };
        buttonFavourite.setOnClickListener(x);

    }
}