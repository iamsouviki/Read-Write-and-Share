package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.example.rws.R.layout.savefiledialogue;

public class CreateNewFile extends AppCompatActivity {

    EditText body,filename;
    String content,FILE_NAME;
    File file,textFile;


    AlertDialog alertDialog,alertDialog1,alert;
    boolean ch;

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_file);

        getSupportActionBar().setTitle("Untitled");
        requestPermissions(permissions,3);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inf = this.getLayoutInflater();

        View dialogView = inf.inflate(savefiledialogue,null);

        builder1.setView(dialogView);
        builder1.setCancelable(true);
        alertDialog1 = builder1.create();

        body = findViewById(R.id.filebody);
        filename = dialogView.findViewById(R.id.filename);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Save File").setMessage("Do you really want to Exit without Saving ?(Y/N)").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(CreateNewFile.this,SecondActivity.class));
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false);

        alertDialog = builder.create();

        dialogView.findViewById(R.id.savefile).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = body.getText().toString();
                FILE_NAME = filename.getText().toString();
                write();
                startActivity(new Intent(CreateNewFile.this,SecondActivity.class));
                finish();
            }
        });

    }

    private void write() {
        try {
            textFile = new File(file,FILE_NAME);
            if(textFile.exists()) {
                if(prepAlert()){
                    FileWriter writer = new FileWriter(textFile,true);
                    writer.append(content+"\n\n");
                    writer.flush();
                    writer.close();
                }
            }else{
                textFile.createNewFile();
                FileWriter writer = new FileWriter(textFile,true);
                writer.append(content+"\n\n");
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), FILE_NAME+"  Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fileeditormenu,menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                alertDialog1.show();
                alertDialog1.setCancelable(false);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        alertDialog.show();
    }

    public boolean prepAlert(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Duplicate File Name");
        build.setMessage("Do you want to delete the previous file ");
        build.setCancelable(false);
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textFile.delete();
                ch = true;
            }
        });
        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                ch = false;
            }
        });

        alert = build.create();
        alert.show();
        return ch;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 3:
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