package com.example.rws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import static com.example.rws.R.layout.savefiledialogue;

public class ShareActivity extends AppCompatActivity {
    AlertDialog alertDialog;
    Button sendfile, recievefile, showpic, showvideos, showapps, showmusic;
    WifiManager wifiManager;
    GridView pictures, videos, music, apps;
    ViewGroup root;
    ImageAdaptar imageAdaptar;
    AppAdaptar appAdaptar;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] DeviceNamearray;
    WifiP2pDevice[] Devicearray;
    WifiP2pManager.PeerListListener peerListListener;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    ListView mobilelist;
    AlertDialog alertDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        getSupportActionBar().setTitle("Share");
        requestPermissions(permissions,5);


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        LayoutInflater inf = this.getLayoutInflater();

        final View dialogView = inf.inflate(R.layout.mobilelist,null);

        builder1.setView(dialogView);
        builder1.setCancelable(true);
        alertDialog1 = builder1.create();
        mobilelist = dialogView.findViewById(R.id.mobilelistview);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {

                List<WifiP2pDevice> refreshedPeers = (List<WifiP2pDevice>) peerList.getDeviceList();
                if (!refreshedPeers.equals(peers)) {
                    peers.clear();
                    peers.addAll(refreshedPeers);
                    DeviceNamearray = new String[peerList.getDeviceList().size()];
                    Devicearray = new WifiP2pDevice[peerList.getDeviceList().size()];
                }
                int index =0;

                for (WifiP2pDevice device : peerList.getDeviceList()) {
                    DeviceNamearray[index]=device.deviceName;
                    Devicearray[index]=device;
                    index++;
                }

                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_2,DeviceNamearray);
                mobilelist.setAdapter(adapter);


                if (peers.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No device found", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        };


        sendfile = findViewById(R.id.send);
        recievefile = findViewById(R.id.recieve);
        showpic = findViewById(R.id.showpics);
        showvideos = findViewById(R.id.showvideos);
        showapps = findViewById(R.id.showapps);
        showmusic = findViewById(R.id.showmusic);

        root = findViewById(R.id.sharecontent);

        appAdaptar = new AppAdaptar(ShareActivity.this);

        root.removeAllViews();
        View appview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallapps, null);
        videos = appview.findViewById(R.id.appGridViewapp);
        videos.setAdapter(appAdaptar);
        root.addView(appview);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


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

        showpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View picview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallpictures, null);
                pictures = picview.findViewById(R.id.galleryGridViewimage);
                imageAdaptar = new ImageAdaptar(ShareActivity.this, "pic");
                pictures.setAdapter(imageAdaptar);
                root.addView(picview);
            }
        });
        showvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallvideos, null);
                videos = videoview.findViewById(R.id.galleryGridViewvideo);
                imageAdaptar = new ImageAdaptar(ShareActivity.this, "video");
                videos.setAdapter(imageAdaptar);
                root.addView(videoview);
            }
        });

        showapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View appview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallapps, null);
                apps = appview.findViewById(R.id.appGridViewapp);
                apps.setAdapter(appAdaptar);
                root.addView(appview);
            }
        });

        //error
        showmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.removeAllViews();
                View videoview = LayoutInflater.from(ShareActivity.this).inflate(R.layout.showallmusics, null);
                music = videoview.findViewById(R.id.galleryGridViewmusic);
                MusicAdaptar musicAdaptar = new MusicAdaptar(ShareActivity.this);
                music.setAdapter(musicAdaptar);
                Toast.makeText(getApplicationContext(), "Coming ", Toast.LENGTH_SHORT).show();
                root.addView(videoview);
            }
        });

        sendfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }

                alertDialog1.show();
                alertDialog1.setCancelable(true);
                if (ActivityCompat.checkSelfPermission(ShareActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Discovering Started", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(getApplicationContext(), "Failed to Start Discovering", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        recievefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(ShareActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ShareActivity.this, "Give Permission", Toast.LENGTH_SHORT).show();
                    return;
                }
                manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Discovering Started", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(getApplicationContext(), "Failed to Start Discovering", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 5:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Please Give Storage Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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


    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


}