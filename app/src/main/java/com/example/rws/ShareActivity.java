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
    Button sendfile,recievefile,showpic,showvideos;
    WifiManager wifiManager;
    GridView gallery;
    ViewGroup root;
    ImageAdaptar imageAdaptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        getSupportActionBar().setTitle("Share");


        sendfile = findViewById(R.id.send);
        recievefile = findViewById(R.id.recieve);
        showpic = findViewById(R.id.showpics);
        showvideos = findViewById(R.id.showvideos);

        root = findViewById(R.id.sharecontent);


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
                View picview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallpictures, null);
                gallery = picview.findViewById(R.id.galleryGridViewimage);
                imageAdaptar = new ImageAdaptar(ShareActivity.this);
                gallery.setAdapter(imageAdaptar);
                root.addView(picview);
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