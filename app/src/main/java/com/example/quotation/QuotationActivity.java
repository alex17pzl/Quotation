package com.example.quotation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        //ImageButton imageButton = findViewById(R.id.imageButton);

        String name = getString(R.string.name);
        String hello = getString(R.string.hello, name);

        tvDentroSv.setText(hello);

        /*View.OnClickListener x = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageButton.isPressed()) {
                    String quotation = getString(R.string.sampleQuotation);
                    String author = getString(R.string.sampleAuthor);

                    tvDentroSv.setText(quotation);
                    sampleText.setText(author);
                }
            }

        };*/

        //imageButton.setOnClickListener(x);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView tvDentroSv = findViewById(R.id.tvDentroSv);
        TextView sampleText = findViewById(R.id.sampleText);

        // Añadir una nueva cita a favoritos
         if (item.getItemId() == R.id.obtain_new_quote) {
             String quotation = getString(R.string.sampleQuotation);
             String author = getString(R.string.sampleAuthor);

             tvDentroSv.setText(quotation);
             sampleText.setText(author);

             return true;
         } else {
             return super.onOptionsItemSelected(item);
         }
    }
}