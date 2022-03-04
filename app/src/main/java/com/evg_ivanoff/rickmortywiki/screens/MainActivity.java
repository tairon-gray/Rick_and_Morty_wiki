package com.evg_ivanoff.rickmortywiki.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.adapters.CharacterAdapter;
import com.evg_ivanoff.rickmortywiki.api.ApiChars;
import com.evg_ivanoff.rickmortywiki.api.ApiService;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterOne;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterResponce;
import com.evg_ivanoff.rickmortywiki.pojo.DataInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCharacters;
    private CharacterAdapter characterAdapter;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;
    private Integer nextPage = 1;
    private Integer prevPage = null;
    private FloatingActionButton btnNext;
    private FloatingActionButton btnPrev;
    private int page;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCharacters = findViewById(R.id.recyclerViewCharacters);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadData(nextPage.intValue());
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadData(prevPage.intValue());
            }
        });

        characterAdapter = new CharacterAdapter();
        characterAdapter.setCharacters(new ArrayList<CharacterOne>());
        recyclerViewCharacters.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewCharacters.setAdapter(characterAdapter);

        //слушатель нажатия на персонажа в recyclerView
        characterAdapter.setOnCharacterClickListener(new CharacterAdapter.OnCharacterClickListener() {
            @Override
            public void onCharacterClick(int position) {
                Intent intent = new Intent(MainActivity.this, CharInfoActivity.class);
                List<CharacterOne> chars = characterAdapter.getCharacters();
                CharacterOne characterOne = chars.get(position);
                intent.putExtra("id", characterOne.getId());
                preferences.edit().putInt("id", characterOne.getId()).apply();
                startActivity(intent);
            }
        });
        downloadData(nextPage);
    }

    public Integer asId(String url) {
        if(url == null){
            return null;
        } else {
            return Integer.parseInt(url.substring(url.lastIndexOf('=') + 1));
        }
    }

    public void downloadData(int id){
        ApiChars apiChars = ApiChars.getInstance();
        ApiService apiService = apiChars.getApiService();
        compositeDisposable = new CompositeDisposable();
        disposable = apiService.getCharactersByPage(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharacterResponce>() {
                    @Override
                    public void accept(CharacterResponce characterResponce) throws Exception {
                        characterAdapter.setCharacters(characterResponce.getResults());
                        DataInfo dataInfo = new DataInfo();
                        dataInfo.setNext(characterResponce.getInfo().getNext());
                        dataInfo.setPrev(characterResponce.getInfo().getPrev());
                        nextPage = asId(dataInfo.getNext());
                        prevPage = asId(dataInfo.getPrev());
                        if(prevPage == null){
                            btnPrev.hide();
                        } else {
                            btnPrev.show();
                        }
                        if(nextPage == null){
                            btnNext.hide();
                        } else {
                            btnNext.show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Fatal Error", Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

}