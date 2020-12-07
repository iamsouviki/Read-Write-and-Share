package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import static com.example.rws.R.layout.savefiledialogue;

public class ShareActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    Button sendfile, recievefile, showpic, showvideos, showapps, showmusic,showfiles;
    GridView pictures, videos, music, apps,folders;
    ViewGroup root;
    ImageAdaptar imageAdaptar;
    AppAdaptar appAdaptar;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    ExtendedFloatingActionButton send,recieve;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        getSupportActionBar().setTitle("Share");
        requestPermissions(permissions,5);

        wifiManager= (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);


        send = findViewById(R.id.send);
        recieve = findViewById(R.id.recieve);
        showpic = findViewById(R.id.showpics);
        showvideos = findViewById(R.id.showvideos);
        showapps = findViewById(R.id.showapps);
        showmusic = findViewById(R.id.showmusic);
        showfiles = findViewById(R.id.showfiles);

        root = findViewById(R.id.sharecontent);

        appAdaptar = new AppAdaptar(ShareActivity.this);

        root.removeAllViews();
        View appview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallapps, null);
        videos = appview.findViewById(R.id.appGridViewapp);
        videos.setAdapter(appAdaptar);
        root.addView(appview);



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
                imageAdaptar = new ImageAdaptar(ShareActivity.this, "pic");
                pictures.setAdapter(imageAdaptar);
                if(imageAdaptar.ImageArray.size()==0){
                    Toast.makeText(getApplicationContext(), "No Photos to Show", Toast.LENGTH_LONG).show();
                }
                root.addView(picview);
            }
        });
        showvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallvideos, null);
                videos = videoview.findViewById(R.id.galleryGridViewvideo);
                imageAdaptar = new ImageAdaptar(ShareActivity.this, "video");
                videos.setAdapter(imageAdaptar);
                if(imageAdaptar.ImageArray.size()==0){
                    Toast.makeText(getApplicationContext(), "No Videos to Show", Toast.LENGTH_LONG).show();
                }
                root.addView(videoview);
            }
        });

        showapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View appview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallapps, null);
                apps = appview.findViewById(R.id.appGridViewapp);
                apps.setAdapter(appAdaptar);
                root.addView(appview);
            }
        });


        showfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                Log.e("File sd",Environment.getExternalStorageDirectory().getName());
                File root1 = new File(Environment.getExternalStorageDirectory(),"");
                View folderview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallfiles,null);
                folders = folderview.findViewById(R.id.folderGridView);

                FileAdaptar fileAdaptar = new FileAdaptar(ShareActivity.this,root1);
                folders.setAdapter(fileAdaptar);
                root.addView(folderview);
            }
        });

        //error
        showmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallmusics, null);
                music = videoview.findViewById(R.id.galleryGridViewmusic);
                MusicAdaptar musicAdaptar = new MusicAdaptar(ShareActivity.this);
                music.setAdapter(musicAdaptar);
                if(musicAdaptar.listOfAllAudioName.size()==0){
                    Toast.makeText(getApplicationContext(), "No Audio to Show", Toast.LENGTH_LONG).show();
                }
                root.addView(videoview);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(ShareActivity.this,WifiDirectActivity.class));
                
            }
        });


        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShareActivity.this,WifiDirectActivity.class));
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 5:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Please Give Storage Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        alertDialog.show();
    }


}