package com.example.quotation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adapter.CustomRecyclerAdapter;
import com.example.pojo.Quotation;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        /*Button buttonFavourite = findViewById(R.id.bAuhorInfo);

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

        buttonFavourite.setOnClickListener(x);*/

        RecyclerView recycler = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recycler.setLayoutManager(manager);

        List<Quotation> quotations = getMockQuotations();
        CustomRecyclerAdapter adapter = new CustomRecyclerAdapter(quotations);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, 1);
        recycler.addItemDecoration(divider);

        recycler.setAdapter(adapter);
    }

    public List<Quotation> getMockQuotations() {
        List<Quotation> quotations = new ArrayList<>();

        Quotation q1 = new Quotation("Cita 1", "Autor 1");
        Quotation q2 = new Quotation("Cita 2", "Autor 2");
        Quotation q3 = new Quotation("Cita 3", "Autor 3");
        Quotation q4 = new Quotation("Cita 4", "Autor 4");
        Quotation q5 = new Quotation("Cita 5", "Autor 5");
        Quotation q6 = new Quotation("Cita 6", "Autor 6");
        Quotation q7 = new Quotation("Cita 7", "Autor 7");
        Quotation q8 = new Quotation("Cita 8", "Autor 8");
        Quotation q9 = new Quotation("Cita 9", "Autor 9");
        Quotation q10 = new Quotation("Cita 10", "Autor 10");
        Quotation q11 = new Quotation("Cita 11", "Autor 11");

        quotations.add(q1);
        quotations.add(q2);
        quotations.add(q3);
        quotations.add(q4);
        quotations.add(q5);
        quotations.add(q6);
        quotations.add(q7);
        quotations.add(q8);
        quotations.add(q9);
        quotations.add(q10);
        quotations.add(q11);

        return quotations;
    }

}