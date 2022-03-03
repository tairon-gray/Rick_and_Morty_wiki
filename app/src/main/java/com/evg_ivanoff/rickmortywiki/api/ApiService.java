package com.evg_ivanoff.rickmortywiki.api;

import com.evg_ivanoff.rickmortywiki.pojo.CharacterOne;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterResponce;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/character")
    Observable<CharacterResponce> getCharacters();

    @GET("api/character/{id}")
    Observable<CharacterOne> getCharacterById(@Path("id") int id);
}
