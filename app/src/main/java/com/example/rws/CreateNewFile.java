package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.rws.R.layout.savefiledialogue;

public class CreateNewFile extends AppCompatActivity {

    EditText body,filename;
    String data,FILE_NAME;



    AlertDialog alertDialog,alertDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_file);

        getSupportActionBar().setTitle("Untitled");

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

        dialogView.findViewById(R.id.savefile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = body.getText().toString();
                FILE_NAME = filename.getText().toString();
                Toast.makeText(getApplicationContext(), data +" "+FILE_NAME, Toast.LENGTH_SHORT).show();
            }
        });

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
}