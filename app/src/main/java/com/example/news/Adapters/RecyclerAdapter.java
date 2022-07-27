package com.example.news.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.ModelClasses.Article;
import com.example.news.R;

import java.util.ArrayList;

//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
//    private static final String TAG = "RecyclerAdapter";
//    private ArrayList<Article> articles;
//    private final Context context;
//    private OnArticleClickedListener onArticleClickedListener;
//    private Button favouriteButton;
//    private Button deleteButton;
//    private String fragment;
//
//    public RecyclerAdapter(Context context, ArrayList<Article> articles, OnArticleClickedListener onArticleClickedListener,String fragment) {
//        this.context = context;
//        this.articles = articles;
//        this.onArticleClickedListener = onArticleClickedListener;
//        this.fragment = fragment;
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder  {
//        private static OnArticleClickedListener onArticleClickedListener;
//        private final Button favouriteButton;
//        private final Button deleteButton;
//        private TextView titleText;
//        private ImageView newsImage;
//        private TextView date;
//
//
//        public MyViewHolder(final View view, OnArticleClickedListener onArticleClickedListener){
//            super(view);
//            titleText = view.findViewById(R.id.headline);
//            newsImage = view.findViewById(R.id.NewsImage);
//            date = view.findViewById(R.id.date);
////            itemView.setOnClickListener(this);
//            MyViewHolder.onArticleClickedListener = onArticleClickedListener;
//            itemView.setOnClickListener(view1 -> onArticleClickedListener.onClickListener(getAdapterPosition()));
//            favouriteButton = view.findViewById(R.id.addFavouritesButton);
//            favouriteButton.setOnClickListener(view1 -> onArticleClickedListener.favouriteClickListener(getAdapterPosition()));
//
//            deleteButton = view.findViewById(R.id.deleteButton);
//            deleteButton.setOnClickListener(view1 -> onArticleClickedListener.deleteClickListener(getAdapterPosition()));
//        }
//
//        static MyViewHolder create(ViewGroup parent){
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card,parent,false);
//            return new MyViewHolder(view, onArticleClickedListener);
//        }
//
//    }
//
//    @NonNull
//    @Override
//    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card,parent,false);
//        return new MyViewHolder(view, onArticleClickedListener);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
//        if (fragment.equals(FragmentEnum.FAVOURITES.name())){
//            favouriteButton.setVisibility(View.GONE);
//        } else {
//            deleteButton.setVisibility(View.GONE);
//        }
//        String headline = articles.get(position).getTitle();
//        holder.titleText.setText(headline);
//        holder.date.setText(articles.get(position).getPublishedAt());
//        Glide.with(context)
//                .load(articles.get(position).getUrlToImage())
//                .override(300,300)
//                .into(holder.newsImage);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return articles.size();
//    }
//
//    public interface OnArticleClickedListener {
//        void onClickListener (int position);
//        void favouriteClickListener(int position);
//        void deleteClickListener(int position);
//    }
//
//    public class ArticleListAdapter extends ListAdapter<Article, MyViewHolder> {
//
//        public ArticleListAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback) {
//            super(diffCallback);
//        }
//
//        @NonNull
//        @Override
//        public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new RecyclerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card, parent,false),onArticleClickedListener);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
//
//            if (fragment.equals(FragmentEnum.FAVOURITES.name())){
//                favouriteButton.setVisibility(View.GONE);
//            } else {
//                deleteButton.setVisibility(View.GONE);
//            }
//            String headline = articles.get(position).getTitle();
//            holder.titleText.setText(headline);
//            holder.date.setText(articles.get(position).getPublishedAt());
//            Glide.with(context)
//                    .load(articles.get(position).getUrlToImage())
//                    .override(300,300)
//                    .into(holder.newsImage);
//        }
//
//        class ArticleDiff extends DiffUtil.ItemCallback<Article> {
//
//            @Override
//            public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
//                return oldItem == newItem;
//            }
//
//            @Override
//            public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
//                return oldItem.getTitle().equals(newItem.getTitle());
//            }
//        }
//    }
//
//
//}


