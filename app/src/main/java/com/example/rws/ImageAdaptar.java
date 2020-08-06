package com.example.rws;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdaptar extends BaseAdapter {
    Context context;
    ArrayList<String> ImageArray;
    String type ;
    String[] projection;
    Uri uri;
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

        ImageView picturesView = view.findViewById(R.id.pictureandvideo);

        Glide.with(context).load(ImageArray.get(i))
                .centerCrop()
                .into(picturesView);

        return picturesView;

    }
    private ArrayList<String> getAllShownImagesPath(Context activity) {

        Cursor cursor;
        int column_index_data, column_index_folder_name;
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

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}
