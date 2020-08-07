package com.example.rws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

public class Contact extends AppCompatActivity {
    Button iti,souvik,sankar,moti,man;
    EditText ig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        iti=findViewById(R.id.iti);
        sankar=findViewById(R.id.sankar);
        souvik=findViewById(R.id.souvik);
        moti=findViewById(R.id.moti);
        man=findViewById(R.id.man);
        ig=findViewById(R.id.editTextTextPersonName2);

        ig.setFocusable(false);
        Animation anim = new AlphaAnimation(1.0f,0.5f);
        anim.setDuration(1000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        ig.startAnimation(anim);

        iti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/iamsouviki/")));

            }
        });
        sankar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/spg_45/")));

            }
        });
        souvik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/vksouvik18/")));

            }
        });
        moti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/s13thirteen/")));

            }
        });
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/soumen748/")));

            }
        });
    }
}