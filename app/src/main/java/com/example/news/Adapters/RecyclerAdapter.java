package com.example.news.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.MainActivity;
import com.example.news.ModelClasses.Article;
import com.example.news.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<Article> articles;
    private Context context;
    private final String TAG = "Debug";
    private OnArticleClickedListener onArticleClickedListener;

    public RecyclerAdapter(Context context, ArrayList<Article> articles, OnArticleClickedListener onArticleClickedListener) {
        this.context = context;
        this.articles = articles;
        this.onArticleClickedListener = onArticleClickedListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleText;
        private ImageView newsImage;
        private TextView date;
        OnArticleClickedListener onArticleClickedListener;

        public MyViewHolder(final View view, OnArticleClickedListener onArticleClickedListener){
            super(view);
            titleText = view.findViewById(R.id.headline);
            newsImage = view.findViewById(R.id.NewsImage);
            date = view.findViewById(R.id.date);
            itemView.setOnClickListener(this);
            this.onArticleClickedListener = onArticleClickedListener;
        }

        @Override
        public void onClick(View view) {
            onArticleClickedListener.onClickListener(getAdapterPosition());
        }
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card,parent,false);
        return new MyViewHolder(view, onArticleClickedListener);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
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
    }
}


