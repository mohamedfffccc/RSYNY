package com.openshop.rsyny.data.api;

import com.openshop.rsyny.data.model.international.International;
import com.openshop.rsyny.data.model.news.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country ,
                       @Query("apiKey") String apiKey);
    @GET("everything")
    Call<News> filterNews(@Query("q") String query ,
                          @Query("from") String from ,
                          @Query("sortBy") String sortBy ,
                          @Query("apikey") String apikey);
    @GET("articles")
    Call<International> getGlobal(@Query("source") String source ,
                                  @Query("sortBy") String sortBy ,
                                  @Query("apiKey") String apiKey);

}
