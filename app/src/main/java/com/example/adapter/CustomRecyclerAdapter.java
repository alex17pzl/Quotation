package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.pojo.Quotation;
import com.example.quotation.R;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

    private List<Quotation> quotations;

    public CustomRecyclerAdapter(List<Quotation> quotations) {
        this.quotations = quotations;
    }

    @NonNull
    @Override
    public CustomRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quotation_item, parent, false);

        CustomRecyclerAdapter.ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapter.ViewHolder holder, int position) {
        holder.quoteText.setText(quotations.get(position).getQuoteText());
        holder.quoteAuthor.setText(quotations.get(position).getQuoteAuthor());
    }

    @Override
    public int getItemCount() {
        return quotations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView quoteText;
        public TextView quoteAuthor;

        public ViewHolder(View view) {
            super(view);
            quoteText = view.findViewById(R.id.tvCita);
            quoteAuthor = view.findViewById(R.id.tvAutor);
        }
    }

}
