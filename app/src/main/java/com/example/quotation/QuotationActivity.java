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

        String hello = getString(R.string.hello);
        String name = getString(R.string.name);

        tvDentroSv.setText(hello.replaceAll("%1s", name));
        View.OnClickListener x = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageButton.isPressed()) {
                    String quotation = getString(R.string.sampleQuotation);
                    String author = getString(R.string.sampleAuthor);

                    tvDentroSv.setText(quotation);
                    sampleText.setText(author);
                }
            }

        };

        imageButton.setOnClickListener(x);
    }
}