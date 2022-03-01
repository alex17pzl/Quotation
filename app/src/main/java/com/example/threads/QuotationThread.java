package com.example.threads;

import com.example.databases.DataBase;
import com.example.pojo.Quotation;
import com.example.fragments.QuotationFragment;

import java.lang.ref.WeakReference;

public class QuotationThread extends Thread {

    private final WeakReference<QuotationFragment> ref;

    public QuotationThread(QuotationFragment activity) {
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
