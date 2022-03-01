package com.example.threads;

import com.example.databases.DataBase;
import com.example.pojo.Quotation;
import com.example.fragments.FavouriteFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class OneThread extends Thread {

    private final WeakReference<FavouriteFragment> reference;

    public OneThread(FavouriteFragment activity) {
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
