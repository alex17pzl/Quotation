package com.example.quotation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        TextView tvDentroSv = findViewById(R.id.tvDentroSv);
        TextView sampleText = findViewById(R.id.sampleText);
        ImageButton imageButton = findViewById(R.id.imageButton);

        String name = getString(R.string.name);

        tvDentroSv.setText("Hello " + name + "\nPress Refresh to get a new quotation");

        View.OnClickListener x = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageButton.isPressed()) {
                    tvDentroSv.setText("This is a sample quotation");
                    sampleText.setText("Sample author");
                }
            }

        };

        imageButton.setOnClickListener(x);
    }
}