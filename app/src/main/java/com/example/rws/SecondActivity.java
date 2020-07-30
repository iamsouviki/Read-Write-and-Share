package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import static com.example.rws.R.layout.openpagedialogue;
import static com.example.rws.R.layout.savefiledialogue;

public class SecondActivity extends AppCompatActivity {

    Button create,note,share,about,openfile;
    int count;
    CarouselView carouselView;
    AlertDialog openpaggedialogue;

    int[] sampleImages = {R.drawable.enjoy,R.drawable.viewimage1,R.drawable.viewimage2,R.drawable.viewimage3};
    ImageListener imageListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportActionBar().setTitle("Read   Write  &  Share");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inf = this.getLayoutInflater();

        final View dialogView = inf.inflate(openpagedialogue,null);

        builder1.setView(dialogView);
        builder1.setCancelable(true);
        openpaggedialogue = builder1.create();

        create = findViewById(R.id.createnewfile);
        note = findViewById(R.id.note);
        share = findViewById(R.id.sharefile);
        about=findViewById(R.id.About);
        openfile=findViewById(R.id.opennewfile);
        carouselView = (CarouselView) findViewById(R.id.carouselView);


        carouselView.setPageCount(sampleImages.length);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,CreateNewFile.class));
                finish();

            }
        });

        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpaggedialogue.show();
                openpaggedialogue.setCancelable(true);
            }
        });


        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this, Note.class));
                finish();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,ShareActivity.class));
                finish();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecondActivity.this,AboutActivity.class));


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.firstmenu,menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count = 0;
            }
        },1500);

        count ++;

        if(count > 1){
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(), "Back Press Again to Exit", Toast.LENGTH_SHORT).show();
    }



}