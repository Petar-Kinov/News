package com.example.news.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private List<Article> favourites;
    private DataBase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        RecyclerView favouritesRecyclerView = view.findViewById(R.id.favouritesView);
        db = Aplication.getInstance();


        //Recycler Adapter
        final ArticleListAdapter articleListAdapter = new ArticleListAdapter(new ArticleListAdapter.ArticleDiff(), this, FragmentEnum.FAVOURITES.name());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        favouritesRecyclerView.setLayoutManager(layoutManager);
        favouritesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favouritesRecyclerView.setAdapter(articleListAdapter);

        FavouritesViewModel favouritesViewModel = new ViewModelProvider(requireActivity()).get(FavouritesViewModel.class);

        favouritesViewModel.getFavouritesLiveData().observe(requireActivity(), articles -> {
            favourites = articles;
            articleListAdapter.submitList(articles);
        });

        //TODO Search in Favourites

        // Search view functionality
//        searchView = view.findViewById(R.id.searchView);
//        searchView.setQueryHint("Search");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                articlesViewModel.setKeyword(searchView.getQuery().toString());
//                searchView.clearFocus();
////                searchView.setIconified(true);
//                return false;
//            }

//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });


        return view;

    }

//    private void setRecyclerAdapter() {
//
//        // TODO Change RecyclerAdapter to ListAdapter for better performance
//
////        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), favourites, this, FragmentEnum.FAVOURITES.name());
//        Log.d(TAG, "setRecyclerAdapter: called");
//
//        final ArticleListAdapter articleListAdapter = new ArticleListAdapter(new ArticleListAdapter.ArticleDiff(), favourites, this, FragmentEnum.FAVOURITES.name());
////        Log.d(TAG, "setRecyclerAdapter: " + articleListAdapter.getCurrentList().get(0).getTitle());
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
//        articleListAdapter.submitList(favourites);
//
//        favouritesRecyclerView.setLayoutManager(layoutManager);
//        favouritesRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        favouritesRecyclerView.setAdapter(articleListAdapter);
//    }

    // opens web View with the clicked article's url
    @Override
    public void onClickListener(int position) {
        openWebViewActivity(favourites.get(position).getUrl());
    }

    @Override
    public void favouriteClickListener(int position) {
        // the favourites button is replaced with delete button in favourites fragment
        // so no functionality is needed here
    }

    @Override
    public void deleteClickListener(int position) {

        Log.d(TAG, "deleteClickListener: clicked delete");
        deleteFavouriteRunnable deleteFavouriteRunnable = new deleteFavouriteRunnable(favourites.get(position));
        new Thread(deleteFavouriteRunnable).start();

//        db.userDao().delete(favourites.get(position));
//        favourites.remove(position);
//        adapter.notifyItemRemoved(position);
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