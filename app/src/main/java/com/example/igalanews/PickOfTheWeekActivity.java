package com.example.igalanews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.igalanews.util.LoginActivity;
import com.example.igalanews.util.SplashLoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PickOfTheWeekActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView pick_of_the_week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_of_the_week);

        pick_of_the_week = findViewById(R.id.img_splash_week);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PickOfTheWeekActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5500);
    }
}