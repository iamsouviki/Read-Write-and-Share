package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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