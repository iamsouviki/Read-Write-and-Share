package com.example.rws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        getSupportActionBar().setTitle("Share");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Back to Main Menu").setMessage("Do You Want to Back (Y/N)").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(ShareActivity.this,SecondActivity.class));
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false);

        alertDialog = builder.create();
    }

    @Override
    public void onBackPressed() {
        alertDialog.show();
    }
}