package com.computech.thenews.sub_sources_fragments;


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
public class BloombergFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MainArticleAdapter adapter;

    public BloombergFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bloomberg, container, false);

        progressBar = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.recycler_source);

        //make http call
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call = apiService.getLatestNewsBySources("bloomberg","popularity",100,
                getResources().getString(R.string.apiKey));

        //get results from http call
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                assert response.body()!=null;
                if (response.body().getStatus().equals("ok")){//http call successful
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size()>0){
                        progressBar.setVisibility(View.GONE);
                        //set up the recycler view
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new MainArticleAdapter(articleList,getActivity());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Failed to obtain news sources",Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                //http call failed
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT)
                        .show();

                //log the error
                Log.e("HTTP Error: ",t.getMessage(),t);
            }
        });


        return  view;
    }

}
