package com.example.rws;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Show_all_files extends ListActivity {
    List<String> listFile=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File root = new File(Environment.getExternalStorageDirectory().getName());
        ListDir(root);

    }
    void ListDir(File f){
        File[] files=f.listFiles();
        listFile.clear();
        for(File file:files){
            listFile.add(file.getName());

        }
        ArrayAdapter<String> dir= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listFile);

        setListAdapter(dir);
    }
}