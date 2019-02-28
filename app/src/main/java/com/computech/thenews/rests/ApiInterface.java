package com.computech.thenews.rests;

import com.computech.thenews.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<ResponseModel> getLatestNewsByCat(
            @Query("country") String country,
            @Query("category") String category,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<ResponseModel> getLatestNewsBySources(
            @Query("sources") String source,
            @Query("sortBy") String sortBy,
            @Query("pageSize")int pageSize,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<ResponseModel> getLatestTrendingNews(
            @Query("language") String language,
            @Query("sources") String sources,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );
}
