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
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.ModelClasses.Article;
import com.example.news.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Article> articles;
    private Context context;
    private final String TAG = "Debug";
    private OnArticleClickedListener onArticleClickedListener;
    private Button favouriteButton;
    private Button deleteButton;
    private String fragment;

    public RecyclerAdapter(Context context, ArrayList<Article> articles, OnArticleClickedListener onArticleClickedListener,String fragment) {
        this.context = context;
        this.articles = articles;
        this.onArticleClickedListener = onArticleClickedListener;
        this.fragment = fragment;
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
            favouriteButton = view.findViewById(R.id.addFavouritesButton);
            favouriteButton.setOnClickListener(view1 -> onArticleClickedListener.favouriteClickListener(getAdapterPosition()));

            deleteButton = view.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(view1 -> onArticleClickedListener.deleteClickListener(getAdapterPosition()));
        }

    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card,parent,false);
        return new MyViewHolder(view, onArticleClickedListener);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        if (fragment.equals(FragmentEnum.FAVOURITES.name())){
            favouriteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.GONE);
        }
        String headline = articles.get(position).getTitle();
        holder.titleText.setText(headline);
        holder.date.setText(articles.get(position).getPublishedAt());
        Glide.with(context)
                .load(articles.get(position).getUrlToImage())
                .override(300,300)
                .into(holder.newsImage);

    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnArticleClickedListener {
        void onClickListener (int position);
        void favouriteClickListener(int position);
        void deleteClickListener(int position);
    }



}


