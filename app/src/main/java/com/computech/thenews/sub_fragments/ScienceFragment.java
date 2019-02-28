package com.computech.thenews.sub_fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.computech.thenews.R;
import com.computech.thenews.adapters.MainArticleAdapter;
import com.computech.thenews.model.Article;
import com.computech.thenews.model.ResponseModel;
import com.computech.thenews.rests.ApiClient;
import com.computech.thenews.rests.ApiInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScienceFragment extends Fragment {
    RecyclerView recyclerView;
    MainArticleAdapter adapter;
    ProgressBar progressBar;


    public ScienceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_science, container, false);

        recyclerView = view.findViewById(R.id.recycler_news_list);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        //create the api interface
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        //make http call
        Call<ResponseModel> call = apiService.getLatestNewsByCat("us","science","popularity",100,getResources().getString(R.string.apiKey));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                assert response.body() != null;
                if (response.body().getStatus().equals("ok")){
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size()>0){
                        //set up the recycler view adapter
                        adapter = new MainArticleAdapter(articleList,getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                Log.e("ERROR: ",t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT)
                        .show();
            }
        });

        return view;
    }

}
