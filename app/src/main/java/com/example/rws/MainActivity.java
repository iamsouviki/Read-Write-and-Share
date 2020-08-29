package com.example.rws;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    Button create,share,openfile;
    int count;
    CarouselView carouselView;
    String actualfilepath,filename;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Animation rotateForward,rotateBackward;
    int[] sampleImages = {R.drawable.enjoy,R.drawable.viewimage1,R.drawable.viewimage2,R.drawable.viewimage3};
    ImageListener imageListener;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Read   Write  &  Share");
        requestPermissions(permissions,3);


        create = findViewById(R.id.createnewfile);

        share = findViewById(R.id.sharefile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this,R.anim.rotate_bacward);

        NavigationView navigationView = findViewById(R.id.navigationView);
        View view = navigationView.inflateHeaderView(R.layout.navigation_header);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserItemSelected(item);
                return false;
            }


            private void UserItemSelected(MenuItem item) {
                switch (item.getItemId()) {

                }

            }
        });



        openfile=findViewById(R.id.opennewfile);
        carouselView = (CarouselView) findViewById(R.id.carouselView);


        carouselView.setPageCount(sampleImages.length);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CreateNewFile.class));
                finish();

            }
        });

        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("text/*");
                startActivityForResult(Intent.createChooser(intent,"Select File"),3);
            }
        });





        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ShareActivity.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);}
        else{
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count = 0;
            }
        },1500);

        count ++;

        if(count > 1){
            finish();
            return;
        }
        Toast.makeText(getApplicationContext(), "Back Press Again to Exit", Toast.LENGTH_SHORT).show();
    }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 3:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Please Give Storage Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            if (filePath.getAuthority().equals("com.android.externalstorage.documents")){

                String tempID = DocumentsContract.getDocumentId(filePath);
                String[] split = tempID.split(":");
                String type = split[0];
                String id = split[1];
                String[] lt = split[1].split("/");
                String im = "";
                for(int i=0;i<lt.length;i++){
                    if(i != lt.length-1){
                        im =im+ "/"+lt[i];
                    }
                }
                if (type.equals("primary")){
                    actualfilepath=  Environment.getExternalStorageDirectory()+""+im;
                }
                if(id.contains("/")){
                    String[] st = id.split("/");
                    filename = st[st.length-1].trim();
                }else{
                    filename = id.trim();
                }

                readfile();

            }

        }
    }

    public void readfile(){
        File file = new File(actualfilepath, filename);
        StringBuilder builder = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine())!=null){
                builder.append(line);
                builder.append("\n");
            }

            br.close();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_LONG).show();
        }
        

        Intent intn = new Intent(MainActivity.this,CreateNewFile.class);
        intn.putExtra("FlieContent",builder.toString());
        intn.putExtra("check",50);
        intn.putExtra("filename",filename);
        startActivity(intn);

    }




}