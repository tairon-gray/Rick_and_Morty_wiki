package com.evg_ivanoff.rickmortywiki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.evg_ivanoff.rickmortywiki.adapters.CharacterAdapter;
import com.evg_ivanoff.rickmortywiki.api.ApiChars;
import com.evg_ivanoff.rickmortywiki.api.ApiService;
import com.evg_ivanoff.rickmortywiki.pojo.Character;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterResponce;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCharacters = findViewById(R.id.recyclerViewCharacters);
        characterAdapter = new CharacterAdapter();
        characterAdapter.setCharacters(new ArrayList<Character>());
        recyclerViewCharacters.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCharacters.setAdapter(characterAdapter);

        ApiChars apiChars = ApiChars.getInstance();
        ApiService apiService = apiChars.getApiService();
        compositeDisposable = new CompositeDisposable();
        disposable = apiService.getCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharacterResponce>() {
                    @Override
                    public void accept(CharacterResponce characterResponce) throws Exception {
                        characterAdapter.setCharacters(characterResponce.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if(compositeDisposable != null){
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}