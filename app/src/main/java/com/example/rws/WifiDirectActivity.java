package com.example.rws;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.example.rws.R.layout.savefiledialogue;
import static com.example.rws.R.layout.wifiondialog;

public class WifiDirectActivity extends AppCompatActivity {

    WifiManager wifiManager;
    WifiManager.LocalOnlyHotspotReservation reservation;
    Boolean wifion=false;
    AlertDialog alertDialog;
    Button wifionbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_direct);
        getSupportActionBar().hide();

        wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //wifi on off for P2P
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inf = this.getLayoutInflater();
        final View dialogView = inf.inflate(wifiondialog,null);
        builder1.setView(dialogView);
        builder1.setCancelable(true);
        alertDialog = builder1.create();
        dialogView.findViewById(R.id.wifionbut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int actualstate = 0;
                try {
                    Method method=wifiManager.getClass().getDeclaredMethod("getWifiApState");
                    method.setAccessible(true);
                    actualstate= (int) method.invoke(wifiManager,(Object[])null);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if(actualstate==13){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        Intent intn = new Intent(Intent.ACTION_MAIN,null);
                        intn.addCategory(Intent.CATEGORY_LAUNCHER);
                        ComponentName cn = new ComponentName("com.android.settings","com.android.settings.TetherSettings");
                        intn.setComponent(cn);
                        intn.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intn);
                    }else{
                        reservation.close();
                    }
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                    wifiManager.setWifiEnabled(true);
                }
                alertDialog.dismiss();
            }
        });

        if(!wifiManager.isWifiEnabled()){
            alertDialog.show();
        }


    }

    @Override
    protected void onResume() {
        if(!wifiManager.isWifiEnabled()){
            alertDialog.show();
        }
        super.onResume();
    }
}