package com.example.threads;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.databases.DataBase;
import com.example.pojo.Quotation;
import com.example.fragments.QuotationFragment;

import java.lang.ref.WeakReference;

public class QuotationThread extends Thread {

    Context context;

    private final WeakReference<QuotationFragment> ref;

    public QuotationThread(QuotationFragment fragment) {
        super();

        this.ref = new WeakReference<>(fragment);
    }

    @Override
    public void run() {
        if (ref.get() != null) {
            Quotation quotation = DataBase.getInstance(ref.get().requireContext()).obtainInterface().obtainQuotation(ref.get().getTvDentroSv());

            ref.get().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ref.get().showAddQuoteToFavouriteOption(quotation);
                }
            });
        }
    }

}
