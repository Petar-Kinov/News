package com.example.news.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Adapters.ArticleListAdapter;
import com.example.news.Aplication;
import com.example.news.DataBase.DataBase;
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.ModelClasses.Article;
import com.example.news.R;
import com.example.news.ViewModels.FavouritesViewModel;
import com.example.news.WebViewActivity;

import java.util.List;


public class FavouritesFragment extends Fragment implements ArticleListAdapter.OnClickListener {

    private static final String TAG = "FavouritesFragment";
    private List<Article> articles;
    private DataBase db;
    private TextView noArticleFoundText ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        RecyclerView favouritesRecyclerView = view.findViewById(R.id.favouritesView);
        noArticleFoundText = view.findViewById(R.id.noArticlesFoundText2);

        db = Aplication.getInstance();

        //Recycler Adapter
        final ArticleListAdapter articleListAdapter = new ArticleListAdapter(new ArticleListAdapter.ArticleDiff(), this, FragmentEnum.FAVOURITES.name());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        favouritesRecyclerView.setLayoutManager(layoutManager);
        favouritesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favouritesRecyclerView.setAdapter(articleListAdapter);

        FavouritesViewModel favouritesViewModel = new ViewModelProvider(requireActivity()).get(FavouritesViewModel.class);

        favouritesViewModel.getFavouritesLiveData().observe(requireActivity(), response -> {
            articles = response;
            articleListAdapter.submitList(response);
            if (response.size() == 0){
                noArticleFoundText.setVisibility(View.VISIBLE);
            } else {
                noArticleFoundText.setVisibility(View.GONE);
            }
        });

        return view;
    }

    // opens web View with the clicked article's url
    @Override
    public void onClickListener(int position) {
        openWebViewActivity(articles.get(position).getUrl());
    }

    @Override
    public void favouriteClickListener(int position) {
        // the favourites button is replaced with delete button in favourites fragment
        // so no functionality is needed here
    }

    @Override
    public void deleteClickListener(int position) {
        Log.d(TAG, "deleteClickListener: clicked delete");
        deleteFavouriteRunnable deleteFavouriteRunnable = new deleteFavouriteRunnable(articles.get(position));
        new Thread(deleteFavouriteRunnable).start();

    }

    public void openWebViewActivity(String url) {
        Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    // Background thread for deleting an article
    class deleteFavouriteRunnable implements Runnable {
        Article article;

        deleteFavouriteRunnable(Article article) {
            this.article = article;
        }

        @Override
        public void run() {
            db.userDao().delete(article);
        }
    }

}