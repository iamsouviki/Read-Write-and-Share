package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFile extends AppCompatActivity {

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    File file,textFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file);

        requestPermissions(permissions,5);

        read();

    }

    private void read() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 5:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    file = new File(Environment.getExternalStorageDirectory(), "RWS");
                    if(file.mkdirs()){
                        Toast.makeText(this, "Setup Done", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}