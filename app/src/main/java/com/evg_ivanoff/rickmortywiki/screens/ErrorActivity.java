package com.evg_ivanoff.rickmortywiki.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.evg_ivanoff.rickmortywiki.R;

public class ErrorActivity extends AppCompatActivity {

    private Button btnError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        btnError = findViewById(R.id.btn_error);

        setTitle("Download error");

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}