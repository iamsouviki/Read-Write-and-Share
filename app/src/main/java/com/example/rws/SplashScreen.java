package com.example.rws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    ImageView ig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ig=findViewById(R.id.ig);
        Toast.makeText(getApplicationContext(), "Thanks for installing before publish", Toast.LENGTH_LONG).show();
        Animation anim = new AlphaAnimation(1.0f,0.5f);
        anim.setDuration(700);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        ig.startAnimation(anim);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        },1500);

    }
}