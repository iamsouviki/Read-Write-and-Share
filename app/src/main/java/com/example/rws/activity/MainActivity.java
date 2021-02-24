package com.example.rws.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rws.R;
import com.google.android.material.navigation.NavigationView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static com.example.rws.R.layout.about;
import static com.example.rws.R.layout.feedback;
import static com.example.rws.R.layout.navigationcreditdialogview;
import static com.example.rws.R.layout.privacypolicies;
import static com.example.rws.R.layout.settingdialog;

public class MainActivity extends AppCompatActivity {

    Button create,share,openfile,technews;
    int count;
    CarouselView carouselView;
    String actualfilepath,filename;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    AlertDialog creditdialog,privacydialog,aboutappdialog,feedbackdialog,settingsdialoge;
    int[] sampleImages = {R.drawable.enjoy,R.drawable.viewimage1,R.drawable.viewimage2,R.drawable.viewimage3};
    ImageListener imageListener;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    Button sendfeedback,cleardata;
    EditText feedbackmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Read   Write  &  Share");
        requestPermissions(permissions,3);

        //drawer layout
        createdrawerLayout();

        initializeItems();

        //carouseview
        carouseviewCreate();

        buttonclicklistener();

        //create all dialog view
        createAllDialogView();


    }

    private void createAllDialogView() {
        //settingdialogview
        AlertDialog.Builder settingbuilder = new AlertDialog.Builder(MainActivity.this);
        final LayoutInflater infset = this.getLayoutInflater();
        final View settingdialogView = infset.inflate(settingdialog,null);
        settingbuilder.setView(settingdialogView);
        settingbuilder.setCancelable(true);
        settingsdialoge = settingbuilder.create();
        cleardata=settingdialogView.findViewById(R.id.clearalldata);
        cleardata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("pm clear YOUR_APP_PACKAGE_GOES HERE");
                    Toast.makeText(getApplicationContext(), "all data cleared", Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "failed to clear data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //creditdialogview
        AlertDialog.Builder creditbuilder = new AlertDialog.Builder(MainActivity.this);
        final LayoutInflater inf = this.getLayoutInflater();
        final View dialogView = inf.inflate(navigationcreditdialogview,null);
        creditbuilder.setView(dialogView);
        creditbuilder.setCancelable(true);
        creditdialog = creditbuilder.create();

        //privacydialogview
        AlertDialog.Builder privacybuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater infp = this.getLayoutInflater();
        final View privacydialogView = infp.inflate(privacypolicies,null);
        privacybuilder.setView(privacydialogView);
        privacydialog = privacybuilder.create();

        //aboutappdialogview
        AlertDialog.Builder aboutbuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater infab = this.getLayoutInflater();
        final View aboutdialogView = infab.inflate(about,null);
        aboutbuilder.setView(aboutdialogView);
        aboutappdialog = aboutbuilder.create();

        //feedbackdialogview
        AlertDialog.Builder feedbackbuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inffed = this.getLayoutInflater();
        final View feedbackdialogView = inffed.inflate(feedback,null);
        feedbackbuilder.setView(feedbackdialogView);
        feedbackbuilder.setCancelable(true);
        feedbackdialog = feedbackbuilder.create();
        sendfeedback=feedbackdialogView.findViewById(R.id.sendfeedback);
        feedbackmessage=feedbackdialogView.findViewById(R.id.feedbackmessage);
        sendfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fmessage=feedbackmessage.getText().toString();
                Uri uri = Uri.parse("smsto:"+"+917001178718");
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                String sharesub="Text";
                intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                intent.putExtra(Intent.EXTRA_TEXT,fmessage);
                intent.setPackage("com.whatsapp");
                feedbackmessage.setText("");
                feedbackdialog.dismiss();
                startActivity(intent);
            }
        });
    }

    private void createdrawerLayout() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationviewside);
        View view = navigationView.inflateHeaderView(R.layout.sidenavigationicon);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserItemSelected(item);
                return false;
            }
            private void UserItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.credit:
                        creditdialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                creditdialog.dismiss();
                            }
                        });
                        creditdialog.show();
                        creditdialog.setCancelable(false);
                        break;
                    case R.id.privacypolicies:
                        privacydialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                privacydialog.dismiss();
                            }
                        });
                        privacydialog.show();
                        privacydialog.setCancelable(false);
                        break;
                    case R.id.aboutapp:
                        aboutappdialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                aboutappdialog.dismiss();
                            }
                        });
                        aboutappdialog.show();
                        aboutappdialog.setCancelable(false);
                        break;
                    case R.id.feedback:
                        feedbackdialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                feedbackdialog.dismiss();
                            }
                        });
                        feedbackdialog.show();
                        feedbackdialog.setCancelable(false);
                        break;
                    case R.id.settings:
                        settingsdialoge.setButton(AlertDialog.BUTTON_NEUTRAL, "Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                settingsdialoge.dismiss();
                            }
                        });
                        settingsdialoge.show();
                        settingsdialoge.setCancelable(false);
                        break;
                }

            }
        });
    }

    private void carouseviewCreate() {
        carouselView.setPageCount(sampleImages.length);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);
    }

    private void initializeItems() {
        //initialize items
        create = findViewById(R.id.createnewfile);
        share = findViewById(R.id.sharefile);
        openfile=findViewById(R.id.opennewfile);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        technews = findViewById(R.id.technews);
    }

    private void buttonclicklistener() {
        //createbutton listener
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateNewFile.class));
                finish();

            }
        });

        //openfile button listener
        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("text/*");
                startActivityForResult(Intent.createChooser(intent,"Select File"),3);
            }
        });

        //share button listener
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WifiDirectActivity.class));
            }
        });

        //technews onclick listener
        technews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewsHomeActivity.class));
            }
        });
    }

    //double back press back create
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
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


    //permission check
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


    //after selecting file via intent
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


    //read a file
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}