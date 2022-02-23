package com.example.threads;

import com.example.databases.DataBase;
import com.example.databases.QuotationDAO;
import com.example.pojo.Quotation;
import com.example.quotation.FavouriteActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class OneThread extends Thread {

    private final WeakReference<FavouriteActivity> reference;

    public OneThread(FavouriteActivity activity) {
        super();
        this.reference = new WeakReference<>(activity);
    }

    @Override
    public void run() {
            if (reference.get() != null) {
                List<Quotation> quotations = DataBase.getInstance(reference.get()).obtainInterface().obtainAllQuotation();
                reference.get().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reference.get().showDeleteAllQuotationsOption(quotations);
                    }
                });
            }
    }
}
