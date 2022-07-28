package com.example.news.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.MainActivity;
import com.example.news.ModelClasses.Article;
import com.example.news.R;

import java.util.List;

public class ArticleListAdapter extends ListAdapter<Article, MyViewHolder> {

    private static final String TAG = "ArticleListAdapter";
    private OnClickListener onClickListener;
    private String fragment;
    private Context context;


    // Glide needs context
    public ArticleListAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback, OnClickListener onClickListener, String fragment) {
        super(diffCallback);
        this.onClickListener = onClickListener;
        this.fragment = fragment;
        Log.d(TAG, "ArticleListAdapter: called");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        this.context = parent.getContext();
        return MyViewHolder.create(parent, onClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Article current = getItem(position);
        holder.bind(context, current, fragment);
    }

    public static class ArticleDiff extends DiffUtil.ItemCallback<Article> {

        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    }

    public interface OnClickListener {
        void onClickListener(int position);

        void favouriteClickListener(int position);

        void deleteClickListener(int position);
    }
}

