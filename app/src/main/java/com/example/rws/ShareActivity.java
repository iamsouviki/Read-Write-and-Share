package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    Button sendfile,recievefile,showpic,showvideos,showapps,showmusic;
    WifiManager wifiManager;
    GridView pictures,videos,music,apps;
    ViewGroup root;
    ImageAdaptar imageAdaptar;
    AppAdaptar appAdaptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        getSupportActionBar().setTitle("Share");


        sendfile = findViewById(R.id.send);
        recievefile = findViewById(R.id.recieve);
        showpic = findViewById(R.id.showpics);
        showvideos = findViewById(R.id.showvideos);
        showapps = findViewById(R.id.showapps);
        showmusic = findViewById(R.id.showmusic);

        root = findViewById(R.id.sharecontent);

        appAdaptar = new AppAdaptar(ShareActivity.this);

        root.removeAllViews();
        View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallapps, null);
        videos = videoview.findViewById(R.id.appGridViewapp);
        videos.setAdapter(appAdaptar);
        root.addView(videoview);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Back to Main Menu").setMessage("Do You Want to Back (Y/N)").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(ShareActivity.this, MainActivity.class));
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false);

        alertDialog = builder.create();
        
        showpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View picview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallpictures, null);
                pictures = picview.findViewById(R.id.galleryGridViewimage);
                imageAdaptar = new ImageAdaptar(ShareActivity.this,"pic");
                pictures.setAdapter(imageAdaptar);
                root.addView(picview);
            }
        });
        showvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallvideos, null);
                videos = videoview.findViewById(R.id.galleryGridViewvideo);
                imageAdaptar = new ImageAdaptar(ShareActivity.this,"video");
                videos.setAdapter(imageAdaptar);
                root.addView(videoview);
            }
        });

        showapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallapps, null);
                apps = videoview.findViewById(R.id.appGridViewapp);
                apps.setAdapter(appAdaptar);
                root.addView(videoview);
            }
        });

        //error
       showmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallmusics, null);
                music = videoview.findViewById(R.id.galleryGridViewmusic);
               /* MusicAdaptar musicAdaptar = new MusicAdaptar(ShareActivity.this);
                music.setAdapter(musicAdaptar);*/
               Toast.makeText(getApplicationContext(), "Coming ", Toast.LENGTH_SHORT).show();
                root.addView(videoview);
            }
        });


    }

    @Override
    public void onBackPressed() {
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_manu,menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_search:
                break;

        }
        return super.onOptionsItemSelected(item);
    }





}