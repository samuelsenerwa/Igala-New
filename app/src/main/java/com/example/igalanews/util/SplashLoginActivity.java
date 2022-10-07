package com.example.igalanews.util;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.igalanews.PickOfTheWeekActivity;
import com.example.igalanews.R;

public class SplashLoginActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        img = findViewById(R.id.img_splash);
       // img.animate().alpha(0).setDuration(0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashLoginActivity.this, PickOfTheWeekActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}