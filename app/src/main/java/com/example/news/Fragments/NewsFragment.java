package com.example.news.Fragments;

import android.content.Intent;
import android.hardware.lights.LightState;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.news.Adapters.ArticleListAdapter;
import com.example.news.ENUMS.FragmentEnum;
import com.example.news.ModelClasses.Article;
import com.example.news.R;
import com.example.news.ViewModels.ArticlesViewModel;
import com.example.news.ViewModels.FavouritesViewModel;
import com.example.news.WebViewActivity;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements ArticleListAdapter.OnClickListener {

    private static final String TAG = "NewsFragment";
    private RecyclerView recyclerView;
    private List<Article> articles;
    private ArticlesViewModel articlesViewModel;
    private FavouritesViewModel favouritesViewmodel;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        articles = new ArrayList<>();

        //Recycler Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ArticleListAdapter adapter = new ArticleListAdapter(new ArticleListAdapter.ArticleDiff(), this, FragmentEnum.NEWS.name());
        adapter.submitList(articles);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        favouritesViewmodel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        articlesViewModel = new ViewModelProvider(requireActivity()).get(ArticlesViewModel.class);
        articlesViewModel.getApiResponseLiveData().observe(requireActivity(), response -> {
            Log.d(TAG, "Live data changed. Size is " + response);
            articles = response;
            adapter.submitList(response);
        });

        // Search view functionality
        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                articlesViewModel.setKeyword(searchView.getQuery().toString());
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;

    }

    @Override
    public void onClickListener(int position) {
        openWebViewActivity(articles.get(position).getUrl());
    }

    @Override
    public void favouriteClickListener(int position) {

        addFavouritesRunnable addFavouritesRunnable = new addFavouritesRunnable(articles.get(position), favouritesViewmodel);
        new Thread(addFavouritesRunnable).start();
    }

    @Override
    public void deleteClickListener(int position) {
        // This button is hidden in the news fragment so this can remain empty
    }


    public void openWebViewActivity(String url) {
        Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);

    }
}

// Background thread for adding favourites
class addFavouritesRunnable implements Runnable {
    Article article;
    FavouritesViewModel favouritesViewmodel;

    // TODO figure out how to access FavouritesViewModel witchout passing it through the favouritesClickListener
    addFavouritesRunnable(Article article, FavouritesViewModel favouritesViewmodel) {
        this.article = article;
        this.favouritesViewmodel = favouritesViewmodel;
    }

    @Override
    public void run() {
        favouritesViewmodel.insertFavourite(article);
    }
}