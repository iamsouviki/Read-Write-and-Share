package com.example.rws.activity;

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
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rws.R;
import com.example.rws.apiclient.CompilerApi;
import com.example.rws.modelclass.CompilerModels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.rws.R.layout.savefiledialogue;

public class CreateNewFile extends AppCompatActivity {

    EditText body,filename,inputs;
    String content,FILE_NAME,openfilepath,inputtext;
    Spinner fileextension,languagespinner;
    Button executecode,takeinput;
    String filecontent;
    String checkoporcr="create";
    int ps;
    String lang1;
    File file,textFile;
    final public static String ClientID = "bbb2483c0ec0540abdcb0b5d455d104a";
    final public static String ClientSecret = "91cc2a1c1b98f9ab8407eda668e08dbc619a9a7c57e017375a65bae9af306d88";

    String worldslist[] = {"abstract","boolean", "break","byte",
            "case","catch","char",
            "class",
            "continue",
            "default",
            "do",
            "double",
            "else",
            "enum",
            "extends",
            "final",
            "finally",
            "float",
            "for","if","implements","import","instanceof","int","interface","long","native","new","null","package","private","protected","public",
    "return","short","static","strictfp","super","synchronized","switch","this","throw","throws","try","void","while"};

    int t=2,mn,k,size=14;


    AlertDialog alertDialog,alertDialog1,alert,alertDialogoutput,alertDialogtakeinput;
    boolean ch;

    ArrayList <String> textcontent = new ArrayList<String>() ;

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String Extn[] = {".txt",".c",".java",".cpp",".xml",".py"};
    String languagelist[] = {"-:Select Language:-","java","c","cpp","python2","python3"};

    View dialogView,dialogviewoutput,dialogviewtakeinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_file);
        getSupportActionBar().setTitle("Untitled");
        requestPermissions(permissions,3);


        //create alertdialog
        createAlertdialog();

        //initailize
        initial();


        // For open file
        comefromopenfile();



        addspan();

    }

    private void comefromopenfile() {
        Intent intn = getIntent();
        final int check = intn.getIntExtra("check",0);
        filecontent = intn.getStringExtra("FlieContent");
        String Filename = intn.getStringExtra("filename");
        openfilepath = intn.getStringExtra("filepath");
        if(check==50){
            getSupportActionBar().setTitle(Filename);
            checkoporcr="open";
            body.setText(filecontent);
            int n=0;
            for(int i=0;i<Filename.length();i++){
                if(Filename.charAt(i)=='.'){
                    n=i;
                    break;
                }
            }
            Filename=Filename.substring(0,n);
            filename.setText(Filename);
        }
    }

    private void initial() {
        body = findViewById(R.id.filecontent);
        executecode = findViewById(R.id.buttonexecute);


        languagespinner = findViewById(R.id.spinnerlanguage);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,languagelist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languagespinner.setAdapter(arrayAdapter);

        languagespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ps=parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        executecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputs.getText().toString();
                String scrp = body.getText().toString();
                if(ps==0){
                    Toast.makeText(getApplicationContext(), "Please Select a Language", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    lang1 = languagelist[ps];
                }
                alertDialogoutput.show();
                compileration(scrp,lang1,input);
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Extn);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileextension.setAdapter(aa);

        //undo-redo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                undo();
                new Handler().postDelayed(this,500);
            }
        },500);

    }

    private void createAlertdialog() {
        //save dialog
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inf = this.getLayoutInflater();
        dialogView = inf.inflate(savefiledialogue,null);
        builder1.setView(dialogView);
        builder1.setCancelable(true);
        alertDialog1 = builder1.create();

        //output dialog
        AlertDialog.Builder builderout = new AlertDialog.Builder(this);
        LayoutInflater infout = this.getLayoutInflater();
        dialogviewoutput = infout.inflate(R.layout.show_output_of_program,null);
        builderout.setView(dialogviewoutput);
        builderout.setCancelable(true);
        alertDialogoutput = builderout.create();
        dialogviewoutput.findViewById(R.id.buttonoutputclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogoutput.dismiss();
            }
        });

        //save/back button
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

        filename = dialogView.findViewById(R.id.filename);
        fileextension = dialogView.findViewById(R.id.fileextension);

        fileextension.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                k=i;
                k=adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialogView.findViewById(R.id.savefile).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        content = body.getText().toString();
                        FILE_NAME = filename.getText().toString()+Extn[k];
                        write();
                        alertDialog1.dismiss();
                    }
                });
        dialogView.findViewById(R.id.backindialogue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog1.dismiss();
            }
        });

        //takeinput dialog
        AlertDialog.Builder buildertakeinput = new AlertDialog.Builder(this);
        LayoutInflater inftakeinput = this.getLayoutInflater();
        dialogviewtakeinput = inftakeinput.inflate(R.layout.take_input,null);
        buildertakeinput.setView(dialogviewtakeinput);
        buildertakeinput.setCancelable(true);
        alertDialogtakeinput = buildertakeinput.create();
        inputs = dialogviewtakeinput.findViewById(R.id.inputedittext);
        dialogviewtakeinput.findViewById(R.id.buttoninputclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputtext=inputs.getText().toString();
                alertDialogtakeinput.dismiss();
            }
        });

        takeinput=findViewById(R.id.buttontakeinput);
        takeinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogtakeinput.show();
            }
        });


    }

    private void addspan() {
        body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    //write a file to storage
    private void write() {
        if(checkoporcr.equals("create")) {
            file = new File(Environment.getExternalStorageDirectory() + "/RWS");
            if (!file.exists()) {
                file.mkdir();
            }
        }else{
          file = new File(openfilepath);
        }
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
            Toast.makeText(getApplicationContext(), "Contacts us if you found problem "+e.toString(), Toast.LENGTH_SHORT).show();
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

    private void compileration(String sc,String lan,String inpt) {
        String url = "https://api.jdoodle.com/v1/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        CompilerApi compilerApi = retrofit.create(CompilerApi.class);
        //String sc = "print('hi')";
        CompilerModels compilerModels = new CompilerModels(ClientID,ClientSecret,sc,inpt,lan,"0");

        Call<CompilerModels> compilerModelsCall = compilerApi.createPost(compilerModels);
        compilerModelsCall.enqueue(new Callback<CompilerModels>() {
            @Override
            public void onResponse(Call<CompilerModels> call, Response<CompilerModels> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Failed Response", Toast.LENGTH_SHORT).show();
                }
                CompilerModels compilerModels1 = response.body();

                TextView output =dialogviewoutput.findViewById(R.id.textViewshowoutput);
                output.setText(compilerModels1.getOutput());
                TextView memory =dialogviewoutput.findViewById(R.id.textViewshowmemory);
                memory.setText(compilerModels1.getMemory());
                TextView time =dialogviewoutput.findViewById(R.id.textViewshowtime);
                time.setText(compilerModels1.getCpuTime());
            }

            @Override
            public void onFailure(Call<CompilerModels> call, Throwable t) {
                Log.e("compile","compile failed");
            }
        });
    }

}