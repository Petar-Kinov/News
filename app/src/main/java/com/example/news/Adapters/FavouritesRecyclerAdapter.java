package com.example.news.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.ModelClasses.Article;
import com.example.news.R;

import java.util.ArrayList;

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.MyViewHolder> {
    private ArrayList<Article> favourites;
    private Context context;
    private final String TAG = "Debug";
    private OnArticleClickedListener onArticleClickedListener;
    private Button deleteButton;

    public FavouritesRecyclerAdapter(Context context, ArrayList<Article> articles, OnArticleClickedListener onArticleClickedListener) {
        this.context = context;
        this.favourites = articles;
        this.onArticleClickedListener = onArticleClickedListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        private TextView titleText;
        private ImageView newsImage;
        private TextView date;
        OnArticleClickedListener onArticleClickedListener;


        public MyViewHolder(final View view, OnArticleClickedListener onArticleClickedListener){
            super(view);
            titleText = view.findViewById(R.id.headline);
            newsImage = view.findViewById(R.id.NewsImage);
            date = view.findViewById(R.id.date);
//            itemView.setOnClickListener(this);
            this.onArticleClickedListener = onArticleClickedListener;
            itemView.setOnClickListener(view1 -> onArticleClickedListener.onClickListener(getAdapterPosition()));
            deleteButton = view.findViewById(R.id.addFavouritesButton);
            deleteButton.setOnClickListener(view1 -> onArticleClickedListener.favouriteClickListener(getAdapterPosition()));
        }

    }

    @NonNull
    @Override
    public FavouritesRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card,parent,false);
        return new MyViewHolder(view, onArticleClickedListener);
    }

    @Override
    public void onBindViewHolder(FavouritesRecyclerAdapter.MyViewHolder holder, int position) {
        String headline = favourites.get(position).getTitle();
        holder.titleText.setText(headline);
        holder.date.setText(favourites.get(position).getPublishedAt());
        Glide.with(context)
                .load(favourites.get(position).getUrlToImage())
                .override(300,300)
                .into(holder.newsImage);

    }


    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public interface OnArticleClickedListener {
        void onClickListener (int position);
        void favouriteClickListener(int position);
    }



}


