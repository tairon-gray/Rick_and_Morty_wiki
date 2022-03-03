package com.evg_ivanoff.rickmortywiki.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.api.ApiChars;
import com.evg_ivanoff.rickmortywiki.api.ApiService;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterOne;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterResponce;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CharInfoActivity extends AppCompatActivity {

    private TextView textCharName;
    private TextView textCharType;
    private TextView textCharLocation;
    private TextView textCharStatus;
    private ImageView imageViewChar;
    private int charId;
    private Disposable disposable;
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);

        textCharName = findViewById(R.id.textCharName);
        textCharType = findViewById(R.id.textCharType);
        textCharLocation = findViewById(R.id.textCharLocation);
        textCharStatus = findViewById(R.id.textCharStatus);
        imageViewChar = findViewById(R.id.imageViewChar);

        Intent intent = getIntent();
        charId = intent.getIntExtra("id",0);

        ApiChars apiChars = ApiChars.getInstance();
        ApiService apiService = apiChars.getApiService();
        compositeDisposable = new CompositeDisposable();
        disposable = apiService.getCharacterById(charId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharacterOne>() {
                    @Override
                    public void accept(CharacterOne characterOne) throws Exception {
                        textCharStatus.setText(characterOne.getName());
                        textCharStatus.setText(characterOne.getStatus());
                        textCharLocation.setText(characterOne.getLocation().getName());
                        textCharType.setText(characterOne.getSpecies());
                        Picasso.get().load(characterOne.getImage()).into(imageViewChar);
                        setTitle(characterOne.getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(CharInfoActivity.this, "World is shutdown", Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}