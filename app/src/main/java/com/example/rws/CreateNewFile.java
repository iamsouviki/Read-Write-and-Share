package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.rws.R.layout.savefiledialogue;

public class CreateNewFile extends AppCompatActivity {

    EditText body,filename;
    String content,FILE_NAME;
    String filecontent;
    File file,textFile;

    int t=2;
    AlertDialog alertDialog,alertDialog1,alert;
    boolean ch;

    ArrayList <String> textcontent = new ArrayList<String>() ;
    ArrayList <String> textcontentt = new ArrayList<String>() ;


    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_file);
        getSupportActionBar().setTitle("Untitled");
        requestPermissions(permissions,3);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inf = this.getLayoutInflater();

        final View dialogView = inf.inflate(savefiledialogue,null);

        builder1.setView(dialogView);
        builder1.setCancelable(true);
        alertDialog1 = builder1.create();

        body = findViewById(R.id.filecontent);
        filename = dialogView.findViewById(R.id.filename);


        // For open file
        Intent intn = getIntent();
        final int check = intn.getIntExtra("check",0);
        filecontent = intn.getStringExtra("FlieContent");
        String Filename = intn.getStringExtra("filename");
        if(check==50){
            getSupportActionBar().setTitle(Filename);
            body.setText(filecontent);
            filename.setText(Filename);
        }


        //undo-redo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                undo();
                new Handler().postDelayed(this,500);
            }
        },500);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Save File").setMessage("Do you really want to Exit without Saving ?(Y/N)").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(CreateNewFile.this, MainActivity.class));
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
            }
        });
        dialogView.findViewById(R.id.backindialogue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
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
                    final Toast toast=Toast.makeText(getApplicationContext(), FILE_NAME+"  Saved Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                    startActivity(new Intent(CreateNewFile.this, MainActivity.class));
                    finish();
                }
            }else{
                textFile.createNewFile();
                FileWriter writer = new FileWriter(textFile,true);
                writer.append(content+"\n\n");
                writer.flush();
                writer.close();
                final Toast toast=Toast.makeText(getApplicationContext(), FILE_NAME+"  Saved Successfully", Toast.LENGTH_SHORT);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                startActivity(new Intent(CreateNewFile.this, MainActivity.class));
                finish();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Contacts us if you found problem", Toast.LENGTH_SHORT).show();
        }

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
            case R.id.undo:
                    int mn = textcontent.size() - t;
                if(textcontent.size()>1 && mn>-1) {
                    body.setText(textcontent.get(mn));
                    t++;
                }else{
                        Toast.makeText(getApplicationContext(), "Nothing to Undo", Toast.LENGTH_SHORT).show();

                }
            case R.id.redo:
                int re = textcontentt.size() -t;
                if(textcontentt.size()<1 && re<-1){
                    body.setText(textcontentt.get(re));
                    t--;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Nothing to Redo", Toast.LENGTH_SHORT).show();

                }


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
        build.setMessage("Want to replace the previous file ");
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
                }
                else{
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void undo(){
        String b = body.getText().toString();
        String c;
        if(textcontent.size()==0){
            c=textcontent.toString();
        }else {
            c = textcontent.get(textcontent.size() - 1).toString();
        }
        if(textcontent.size()==0 ){
            textcontent.add("");
            //Toast.makeText(getApplicationContext(), "Running", Toast.LENGTH_LONG).show();
        }else{
            if(!c.equals(b) && !textcontent.contains(b)){
                textcontent.add(b);
               // Toast.makeText(getApplicationContext(), textcontent.toString()+"", Toast.LENGTH_LONG).show();
            }
        }
    }
}