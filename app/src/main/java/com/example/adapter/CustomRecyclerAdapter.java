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
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public CustomRecyclerAdapter(List<Quotation> quotations, OnItemClickListener onItemClickListener) {
        this.quotations = quotations;
        this.onItemClickListener = onItemClickListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView quoteText;
        public TextView quoteAuthor;

        public ViewHolder(View view) {
            super(view);
            quoteText = view.findViewById(R.id.tvCita);
            quoteAuthor = view.findViewById(R.id.tvAutor);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(quotations.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemLongClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }

            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Quotation quotation);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void deleteItem(int position) {
        quotations.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAllQuotations() {
        quotations.removeAll(quotations);
        notifyDataSetChanged();
    }

    public void addFavouriteQuotationsToStoredList(List<Quotation> quotes) {
        quotations.addAll(quotes);
        notifyDataSetChanged();
    }

    public Quotation getQuotation(int position) {
        return quotations.get(position);

    }

    public void addQuoteAtGivenPosition(Quotation quotation, int position) {
        quotations.add(position, quotation);
        notifyDataSetChanged();
    }

}
