package com.example.news.Adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.news.MainActivity;
import com.example.news.ModelClasses.Article;
import com.example.news.R;

//
public class MyViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MyViewHolder";
    private final TextView title;
    private Button favouriteButton;
    private Button deleteButton;
    private ImageView newsImage;
    private TextView date;

    public MyViewHolder(@NonNull View itemView, ArticleListAdapter.OnClickListener onClickListener) {
        super(itemView);
        title = itemView.findViewById(R.id.headline);
        favouriteButton = itemView.findViewById(R.id.addFavouritesButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);
        newsImage = itemView.findViewById(R.id.NewsImage);
        date = itemView.findViewById(R.id.date);
        itemView.setOnClickListener(view -> onClickListener.onClickListener(getAdapterPosition()));
        favouriteButton.setOnClickListener(view -> onClickListener.favouriteClickListener(getAdapterPosition()));
        deleteButton.setOnClickListener(view -> onClickListener.deleteClickListener(getAdapterPosition()));
    }

    public void bind(Context context, Article article, String fragment) {
        Log.d(TAG, "bind: called" + article.getTitle());
        title.setText(article.getTitle());
        date.setText(article.getPublishedAt());
        Glide.with(context)
                .load(article.getUrlToImage())
                .override(300, 300)
                .into(newsImage);
        if (fragment.equals(FragmentEnum.FAVOURITES.name())) {
            favouriteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    static MyViewHolder create(ViewGroup parent, ArticleListAdapter.OnClickListener onClickListener) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card, parent, false);
        Log.d(TAG, "create: my view holder called");
        return new MyViewHolder(view, onClickListener);
    }
}
