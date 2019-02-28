package com.computech.thenews.sub_sources_fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
public class AljerzeeraFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MainArticleAdapter adapter;

    public AljerzeeraFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aljerzeera,container,false);

        recyclerView = view.findViewById(R.id.recycler_source);
        progressBar = view.findViewById(R.id.progress);

        //make http call
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call = apiService.getLatestNewsBySources("al-jazeera-english","popularity",100,
                getResources().getString(R.string.apiKey));

        //get results from http call
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                assert response.body() !=null;
                if (response.body().getStatus().equals("ok")){//http call was successful
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size()>0){
                        //set up the recycler adapter
                        adapter = new MainArticleAdapter(articleList,getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Failed to obtain news results, try again later",Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                //show internet error to user and log the errors into console
                Toast.makeText(getActivity(),"Please check your internet connection",Toast.LENGTH_SHORT)
                        .show();

                Log.e("HTTP Error: ",""+t.getMessage(),t);

                progressBar.setVisibility(View.GONE);
            }
        });

        return view;
    }
}
