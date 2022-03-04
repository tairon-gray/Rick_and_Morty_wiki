package com.evg_ivanoff.rickmortywiki.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.api.ApiChars;
import com.evg_ivanoff.rickmortywiki.api.ApiService;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterOne;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    private Button btnEpisodes;
    private int charId;
    private final List<Integer> episodesId = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);

        textCharName = findViewById(R.id.textCharName);
        textCharType = findViewById(R.id.textCharType);
        textCharLocation = findViewById(R.id.textCharLocation);
        textCharStatus = findViewById(R.id.textCharStatus);
        imageViewChar = findViewById(R.id.imageViewChar);
        btnEpisodes = findViewById(R.id.btnEpisodes);

        btnEpisodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEp = new Intent(CharInfoActivity.this, Episodes.class);
                intentEp.putExtra("epId", charId);
                intentEp.putExtra("charName", textCharName.getText());
                intentEp.putIntegerArrayListExtra("episodesId", (ArrayList<Integer>) episodesId);
                startActivity(intentEp);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CharInfoActivity.this);
        charId = preferences.getInt("id", 0);

        ApiChars apiChars = ApiChars.getInstance();
        ApiService apiService = apiChars.getApiService();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = apiService.getCharacterById(charId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharacterOne>() {
                    @Override
                    public void accept(CharacterOne characterOne) throws Exception {
                        textCharName.setText(characterOne.getName());
                        textCharStatus.setText(characterOne.getStatus());
                        textCharLocation.setText(characterOne.getLocation().getName());
                        textCharType.setText(characterOne.getSpecies());
                        List<String> ss = characterOne.getEpisode();
                        for (int i = 0; i < ss.size(); i++) {
                            episodesId.add(asId(ss.get(i)));
                        }
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

    public List<Integer> getEpisodesId() {
        return episodesId;
    }

    public Integer asId(String url) {
        return Integer.parseInt(url.substring(url.lastIndexOf('/') + 1));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("Shared", "назад");
        return super.onOptionsItemSelected(item);
    }
}