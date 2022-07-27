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
    public ArticleListAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback,OnClickListener onClickListener, String fragment) {
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
        holder.bind(context,current, fragment);
    }

    public static class ArticleDiff extends DiffUtil.ItemCallback<Article> {

        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }


    }


    public interface OnClickListener {
        void onClickListener (int position);
        void favouriteClickListener(int position);
        void deleteClickListener(int position);
    }
}
//    protected ArticleListAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
//        super(diffCallback);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//class ArticleListAdapter extends ListAdapter<Article, MyViewHolder> {
//
//    protected ArticleListAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback) {
//        super(diffCallback);
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card, parent,false), this );
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//    }
//}


//}
//public class ArticleListAdapter extends ListAdapter<Article, ArticleListAdapter> {
//
//    public ArticleListAdapter(@NonNull DiffUtil.ItemCallback<Article> diffCallback) {
//        super(diffCallback);
//    }
//
//    @Override
//    public ArticleListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
//        return ArticleListAdapter.;
//    }
//
//    @Override
//    public void onBindViewHolder(ArticleListAdapter holder, int position) {
//        Article current = getItem(position);
//        holder.bind(current.getWord());
//    }
//
//    static class WordDiff extends DiffUtil.ItemCallback<Word> {
//
//        @Override
//        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
//            return oldItem == newItem;
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
//            return oldItem.getWord().equals(newItem.getWord());
//        }
//    }

