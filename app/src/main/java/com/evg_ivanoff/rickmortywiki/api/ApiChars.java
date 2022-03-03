package com.evg_ivanoff.rickmortywiki.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiChars {
    private static ApiChars apiChars;
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://rickandmortyapi.com/";

    private ApiChars(){
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiChars getInstance(){
        if(apiChars == null){
            apiChars = new ApiChars();
        }
        return apiChars;
    }

    public ApiService getApiService(){
        return retrofit.create(ApiService.class);
    }
}
