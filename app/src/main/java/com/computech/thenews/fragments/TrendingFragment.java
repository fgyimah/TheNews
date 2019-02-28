package com.computech.thenews.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.computech.thenews.R;
import com.computech.thenews.adapters.MainArticleAdapter;
import com.computech.thenews.model.Article;
import com.computech.thenews.model.ResponseModel;
import com.computech.thenews.rests.ApiClient;
import com.computech.thenews.rests.ApiInterface;


import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MainArticleAdapter adapter;

    public TrendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Trending");

        recyclerView = view.findViewById(R.id.recycler_trending);
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(true);
            }
        });

        loadNews(false);


        return view;
    }

    private void loadNews(boolean isRefreshed) {
        if (!isRefreshed){

            //create the api interface
            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            //make http call
            Call<ResponseModel> call = apiService.getLatestTrendingNews("en","bbc-news,cnn,bloomberg","publishedAt",100,getResources().getString(R.string.apiKey));
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals("ok")){
                        List<Article> articleList = response.body().getArticles();
                        if (articleList.size()>0){
                            swipeRefreshLayout.setVisibility(View.VISIBLE);

                            //set up the recycler view adapter
                            adapter = new MainArticleAdapter(articleList,getActivity());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                    Log.e("ERROR: ",t.getMessage());
                    Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }


}
