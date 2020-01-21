package com.example.android35.app;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android35.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeremytyson ryantownsend
 * adapter to populate the grid view of photos
 */
public class PhotoAdapter extends ArrayAdapter<Photo> {
    /**
     * context we are currently in
     */
    private Context context;
    /**
     * list of photos
     */
    private List<Photo> photoList = new ArrayList<Photo>();

    /**
     * @author jeremytyson ryantownsend
     * @param context
     * @param list
     * constructor for adapter instance of photo
     */
    public PhotoAdapter(Context context, List<Photo> list){
        super(context, 0, list);
        this.context = context;
        this.photoList = list;
    }

    /**
     * @author jeremytyson ryantownsend
     * @param location
     * @return
     * check to see if user is trying to add a duplicate photo to same album
     */
    private boolean photoDuplicate(Uri location) {
        for (Photo p : photoList) {
            if (p.getUri().equals(location))
                return true;
        }

        return false;
    }

    /**
     * @author jeremytyson ryantownsend
     * @param object
     * @return
     * adds the photo to the album
     */
    public boolean addPhoto(Photo object) {
        if (photoDuplicate(object.getUri())) {
            return false;
        } else {
            add(object);
            return true;
        }
    }

    /**
     * @author jeremytyson ryantownsend
     * @param object
     * overrides add
     */
    @Override
    public void add(Photo object) {
        super.add(object);
    }

    @Override
    public void remove(Photo object) {
        super.remove(object);
    }

    /**
     * @author jeremytyson ryantownsend
     * @return
     * gets the photo count of album
     */
    @Override
    public int getCount() {
        return photoList.size();
    }

    /**
     * @author jeremytyson ryantownsend
     * @param position
     * @return
     * gets the selected item
     */
    @Override
    public Photo getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * @author jeremytyson ryantownsend
     * @param position
     * @param convertView
     * @param parent
     * @return
     * inflater to properly display listview items
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Photo p = photoList.get(position);
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        }

        ImageView imageView = (ImageView) listItem.findViewById(R.id.imageView);


        //Bitmap bmImg = BitmapFactory.decodeFile(p.getPath());


      //  imageView.setImageBitmap(bitmap);
        imageView.setImageURI(p.getUri());
        //imageView.setMaxHeight(100);
        //imageView.setMaxWidth(100);
        return listItem;
    }



}

