package com.example.rws;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdaptar extends BaseAdapter {
    List<String> listFile = new ArrayList<String>();
    Context context;
    File root;
    LayoutInflater layoutInflater;

    FileAdaptar(Context context1, File root) {
        this.context = context1;
        this.root = root;
        ListDir(root);

    }

    @Override
    public int getCount() {
        return listFile.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = layoutInflater.inflate(R.layout.gridfile,null);

        }

        TextView foldername = view.findViewById(R.id.foldernameout);

        foldername.setText(listFile.get(i).toString());

        return view;
    }

    void ListDir(File f) {
        File[] files = f.listFiles();
        //listFile.clear();
        for (File file : files) {
            listFile.add(file.getName());

        }
    }
}
