package com.example.rws;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdaptar extends BaseAdapter {
    Context context;
    List<String> ImageArray;
    String type ;
    String[] projection;
    Uri uri;
    ImageView picturesView;
    LayoutInflater layoutInflater;

    ImageAdaptar(Context context,String type){
        this.context=context;
        this.type = type;
        ImageArray = getAllShownImagesPath(context);

    }
    @Override
    public int getCount() {
        return ImageArray.size();
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
           view = layoutInflater.inflate(R.layout.gridimages,null);

        }

        picturesView = view.findViewById(R.id.pictureandvideo);
        Log.e("Images",ImageArray.get(i));
        Glide.with(context).load(Uri.fromFile(new File(ImageArray.get(i))))
                .apply(new RequestOptions().override(100, 100))
                .into(picturesView);

        return view;

    }
    private List<String> getAllShownImagesPath(Context activity) {

        Cursor cursor;
        int data;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        if(type == "pic"){
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        projection = new String[]{MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        }else if(type == "video") {
            uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            projection = new String[]{MediaStore.MediaColumns.DATA,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME};
        }


        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}
