package com.example.myapplication.ui.main.screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.data.network.responses.BooksResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private List<BooksResponse> dataset = new ArrayList<>();
    private Context context;

    public BooksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(dataset.get(position), context);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setData(List<BooksResponse> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvTitle);
            imageView = itemView.findViewById(R.id.ivImage);
        }

        public void bind(BooksResponse book, Context context) {
            String imageUrl = book.getImageUrl();
            imageUrl = imageUrl.replace("http","https");

            Picasso.with(context)
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(imageView);

            textView.setText(book.getTitle());
        }
    }

}
