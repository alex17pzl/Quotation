package com.example.quotation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.databases.DataBase;
import com.example.databases.QuotationDAO;
import com.example.pojo.Quotation;

public class QuotationActivity extends AppCompatActivity {

    private int quotesReceived = 0;
    Menu thisMenu;
    boolean addVisible;
    TextView tvDentroSv;
    TextView sampleText;

    private QuotationDAO quotationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvDentroSv = findViewById(R.id.tvDentroSv);
        sampleText = findViewById(R.id.sampleText);

        quotationDAO = DataBase.getInstance(this).obtainInterface();

        if (savedInstanceState != null) {

            int currentNumberLabel = savedInstanceState.getInt("currentNumberLabel");
            String quotation = savedInstanceState.getString("quotation");
            String author = savedInstanceState.getString("author");
            addVisible = savedInstanceState.getBoolean("addVisible");

            tvDentroSv.setText(quotation);
            sampleText.setText(author);
            quotesReceived = currentNumberLabel;

        } else {

            //TextView tvDentroSv = findViewById(R.id.tvDentroSv);
            //TextView sampleText = findViewById(R.id.sampleText);
            //ImageButton imageButton = findViewById(R.id.imageButton);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            String username = prefs.getString("username", "");
            String name = getString(R.string.name);

            if (username.equals("")) {
                username = name;
            }

            String hello = getString(R.string.hello, username);

            tvDentroSv.setText(hello);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        thisMenu = menu;
        thisMenu.findItem(R.id.add_quote_obtained).setVisible(addVisible);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        tvDentroSv = findViewById(R.id.tvDentroSv);
        sampleText = findViewById(R.id.sampleText);

        String quotation = getString(R.string.sampleQuotation, quotesReceived);
        String author = getString(R.string.sampleAuthor, quotesReceived);

        // Añadir una nueva cita a favoritos
         if (item.getItemId() == R.id.obtain_new_quote) {
             quotesReceived++;

             tvDentroSv.setText(quotation);
             sampleText.setText(author);

             if (quotationDAO.obtainQuotation(tvDentroSv.getText().toString()) == null) {
                 addVisible = true;
                 thisMenu.findItem(R.id.add_quote_obtained).setVisible(true);
             } else {
                 addVisible = false;
                 thisMenu.findItem(R.id.add_quote_obtained).setVisible(false);
             }

             return true;
         } else {
             // Añadir a favoritos
             if (item.getItemId() == R.id.add_quote_obtained) {
                 thisMenu.findItem(R.id.add_quote_obtained).setVisible(false);
                 quotationDAO.addQuotation(new Quotation(tvDentroSv.getText().toString(), sampleText.getText().toString()));

                 return true;
             } else {
                 return super.onOptionsItemSelected(item);
             }
         }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("quotation", tvDentroSv.getText().toString());
        outState.putString("author", sampleText.getText().toString());
        outState.putInt("currentNumberLabel", quotesReceived);
        outState.putBoolean("addVisible", (thisMenu.findItem(R.id.add_quote_obtained).isVisible()));
    }
}