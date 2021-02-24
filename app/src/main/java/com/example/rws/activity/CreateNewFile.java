package com.example.rws.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rws.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.rws.R.layout.savefiledialogue;

public class CreateNewFile extends AppCompatActivity {

    EditText body,filename;
    String content,FILE_NAME;
    Spinner fileextension;
    String filecontent;
    File file,textFile;

    int t=2,mn,k,size=14;


    AlertDialog alertDialog,alertDialog1,alert;
    boolean ch;

    ArrayList <String> textcontent = new ArrayList<String>() ;

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String Extn[] = {".txt",".c",".java",".cpp",".xml"};

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
        fileextension = dialogView.findViewById(R.id.fileextension);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Extn);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileextension.setAdapter(aa);

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
                fileextension.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        k=i;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                FILE_NAME = filename.getText().toString()+Extn[k];
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


    //write a file to storage
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

    //creating option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fileeditormenu,menu);
        return (true);
    }

    //option menu items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                alertDialog1.show();
                alertDialog1.setCancelable(false);
                break;
            case R.id.undo:
                if(textcontent.size()>1 ) {
                    mn = textcontent.size() - t;
                    if(mn<0){
                        body.setText("");
                    }
                    body.setText(textcontent.get(mn));
                    t++;
                }else{


                }
                break;
            case R.id.redofile:
                if(textcontent.size()>1 && mn+1<textcontent.size()){
                   body.setText(textcontent.get(mn+1));
                    //Toast.makeText(getApplicationContext(), textcontent.get(mn+1)+" "+mn, Toast.LENGTH_SHORT).show();
                    mn++;
                }
                break;
            case R.id.zoomintext:
                size = size+2;
                body.setTextSize(size);
                break;
            case R.id.zoomout:
                if(size>7) {
                    size = size - 2;
                    body.setTextSize(size - 2);
                }
                break;
            case R.id.share:
                Intent myIntent= new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String sharebody=body.getText().toString();
                String sharesub="Text";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                myIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
                startActivity(Intent.createChooser(myIntent,"Share using"));
        }
        return super.onOptionsItemSelected(item);
    }

    //backpress
    @Override
    public void onBackPressed() {
        alertDialog.show();

    }


    //check the file present on the device or not
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

    //check permissions
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

    //undo function
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
        }else{
            if(!c.equals(b) && !textcontent.contains(b)){
                textcontent.add(b);
            }
        }
    }
}