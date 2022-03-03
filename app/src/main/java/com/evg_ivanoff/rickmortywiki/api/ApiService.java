package com.evg_ivanoff.rickmortywiki.api;

import com.evg_ivanoff.rickmortywiki.pojo.CharacterOne;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterResponce;
import com.evg_ivanoff.rickmortywiki.pojo.Episode;
import com.evg_ivanoff.rickmortywiki.pojo.EpisodeResponce;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/character")
    Observable<CharacterResponce> getCharacters();

    @GET("api/character/{id}")
    Observable<CharacterOne> getCharacterById(@Path("id") int id);

    @GET("api/episode")
    Observable<EpisodeResponce> getEpisodes();

    @GET("api/episode/{id}")
    Observable<Episode> getEpisodeById(@Path("id") int id);
}
