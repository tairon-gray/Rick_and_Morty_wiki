package com.evg_ivanoff.rickmortywiki.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.adapters.EpisodeAdapter;
import com.evg_ivanoff.rickmortywiki.api.ApiChars;
import com.evg_ivanoff.rickmortywiki.api.ApiService;
import com.evg_ivanoff.rickmortywiki.pojo.Episode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Episodes extends AppCompatActivity {

    private RecyclerView recyclerViewEpisodes;
    private List<Episode> episodesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        recyclerViewEpisodes = findViewById(R.id.recyclerViewEpisodes);
        EpisodeAdapter episodeAdapter = new EpisodeAdapter();
        episodeAdapter.setEpisodes(new ArrayList<Episode>());
        recyclerViewEpisodes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEpisodes.setAdapter(episodeAdapter);

        Intent intent = getIntent();
        int episodeId = intent.getIntExtra("epId",1);
        String charName = intent.getStringExtra("charName");

        List<Integer> episodesId = intent.getIntegerArrayListExtra("episodesId");
        Log.i("MyResult", ""+episodesId.size());

        for(Integer id:episodesId) {
            ApiChars apiChars = ApiChars.getInstance();
            ApiService apiService = apiChars.getApiService();
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = apiService.getEpisodeById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Episode>() {
                        @Override
                        public void accept(Episode episode) throws Exception {
                            Log.i("MyResult", "cj");
                            episodesList.add(episode);
                            Collections.sort(episodesList);
                            episodeAdapter.setEpisodes(episodesList);
                            setTitle(charName+"'s episodes");
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Intent intent = new Intent(Episodes.this, ErrorActivity.class);
                            startActivity(intent);
                            Episodes.super.finish();
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }
}