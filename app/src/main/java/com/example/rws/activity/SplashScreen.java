package com.example.rws.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.rws.R;

public class SplashScreen extends AppCompatActivity {
    ImageView ig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getSupportActionBar().hide();

        //set animation in splashscreen
        ig=findViewById(R.id.ig);
        Animation anim = new AlphaAnimation(1.0f,0.5f);
        anim.setDuration(700);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        ig.startAnimation(anim);

        //splashscreen create
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        },1500);

    }
}