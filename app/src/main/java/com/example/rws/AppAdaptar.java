package com.example.rws;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AppAdaptar extends BaseAdapter {

    ArrayList<String> AppNamelist;
    ArrayList<Drawable> AppIconlist;
    Context context;
    LayoutInflater layoutInflater;

    AppAdaptar(Context context){
        this.context = context;
        loadApps();
    }

    @Override
    public int getCount() {
        return AppIconlist.size();
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
            view = layoutInflater.inflate(R.layout.gridapps,null);

        }

        ImageView picturesView = view.findViewById(R.id.appiconimage);
        TextView appname = view.findViewById(R.id.appnameout);
        Glide.with(context).load(AppIconlist.get(i))
                .centerCrop()
                .into(picturesView);
        appname.setText(AppNamelist.get(i));

        return view;
    }

    public void loadApps() {
        try {
            List <PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
            AppNamelist = new ArrayList<String>();
            AppIconlist = new ArrayList<android.graphics.drawable.Drawable>();
            for(int j=0;j<packageInfoList.size();j++){
                PackageInfo packageInfo = packageInfoList.get(j);
                if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==0) {
                    AppNamelist.add(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                    AppIconlist.add(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
