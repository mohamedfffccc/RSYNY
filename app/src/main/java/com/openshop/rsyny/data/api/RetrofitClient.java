package com.openshop.rsyny.data.api;

import com.openshop.rsyny.data.api.NewsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL="https://newsapi.org/v2/";
    static final String BASE2_URL = "https://newsapi.org/v1/";
    static   Retrofit retrofit=null;
    public static NewsApi getClient()
    {
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsApi.class);
    }
    public static NewsApi getClient2()
    {
      Retrofit  retrofit2=new Retrofit.Builder().baseUrl(BASE2_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit2.create(NewsApi.class);
    }
}
