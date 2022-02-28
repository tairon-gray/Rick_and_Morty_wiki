package com.evg_ivanoff.rickmortywiki.api;

import com.evg_ivanoff.rickmortywiki.pojo.CharacterResponce;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("character")
    Observable<CharacterResponce> getCharacters();
}
