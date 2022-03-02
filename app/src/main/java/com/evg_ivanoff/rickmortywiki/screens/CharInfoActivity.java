package com.evg_ivanoff.rickmortywiki.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.evg_ivanoff.rickmortywiki.R;
import com.squareup.picasso.Picasso;

public class CharInfoActivity extends AppCompatActivity {

    private TextView textCharName;
    private TextView textCharType;
    private TextView textCharLocation;
    private TextView textCharStatus;
    private ImageView imageViewChar;


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
        textCharName.setText(intent.getStringExtra("name"));
        textCharType.setText(intent.getStringExtra("vid"));
        textCharLocation.setText(intent.getStringExtra("mesto"));
        textCharStatus.setText(intent.getStringExtra("status"));
        Picasso.get().load(intent.getStringExtra("img")).into(imageViewChar);

        setTitle(intent.getStringExtra("name"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}