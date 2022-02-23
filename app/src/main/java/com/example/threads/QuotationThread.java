package com.example.threads;

import com.example.databases.DataBase;
import com.example.pojo.Quotation;
import com.example.quotation.FavouriteActivity;
import com.example.quotation.QuotationActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class QuotationThread extends Thread {

    private final WeakReference<QuotationActivity> ref;

    public QuotationThread(QuotationActivity activity) {
        super();
        this.ref = new WeakReference<>(activity);
    }

    @Override
    public void run() {

        if (ref.get() != null) {

            Quotation quotation = DataBase.getInstance(ref.get()).obtainInterface().obtainQuotation(ref.get().getTvDentroSv());

            ref.get().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ref.get().showAddQuoteToFavouriteOption(quotation);
                }
            });

        }

    }

}
