package com.example.rws;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdaptar extends BaseAdapter {
    Context context;
    ArrayList<String> listOfAllAudioName;
    ArrayList<String> listOfAllAudio;
    String[] projection;
    Uri uri;
    LayoutInflater layoutInflater;

    MusicAdaptar(Context context){
        this.context=context;
        listOfAllAudioName = new ArrayList<>();
        listOfAllAudio=getAllShownAudioName(context);

    }

    @Override
    public int getCount() {
        return listOfAllAudio.size();
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
            view = layoutInflater.inflate(R.layout.gridmusic,null);

        }
        TextView musicn = view.findViewById(R.id.musicnameout);

        String musicname[] = listOfAllAudioName.get(i).split("/");

        musicn.setText(musicname[musicname.length-1]);

        return view;
    }
    private ArrayList<String> getAllShownAudioName(Context activity) {

        Cursor cursor;
        int data;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = "5";

            uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            projection = new String[]{MediaStore.MediaColumns.DATA,
                    MediaStore.Audio.Media.DISPLAY_NAME};


        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(data);
            if(absolutePathOfImage==null){
                absolutePathOfImage = "souvik";
            }
            listOfAllAudioName.add(absolutePathOfImage);
        }
        Log.e("Total Music Files",listOfAllAudioName.size()+"");
        return  listOfAllAudioName;
    }
}
