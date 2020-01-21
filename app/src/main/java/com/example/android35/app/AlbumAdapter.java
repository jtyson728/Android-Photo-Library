package com.example.android35.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android35.R;

import java.util.List;

//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;

/**
 * @author ryantownsend
 * adapter that will populate the list of albums
 */
public class AlbumAdapter extends ArrayAdapter<Album> {
    /**
     * variable to hold list of albums
     */
    private List<Album> albumList;
    /**
     * refers to the context we are currently in
     */
    private Context currContext;

    /**
     * @author ryantownsend
     * @param c
     * @param inputList
     * constructor for an instance of albumadapter
     */
    public AlbumAdapter( Context c, List<Album> inputList){
        super(c, 0 , inputList);
        albumList = inputList;
        currContext = c;
    }

    /**
     * @author ryantownsend
     * @param albumName
     * check to see if an album name is a duplicate or not when we are trying to add
     */
    private boolean isDuplicate(String albumName) {
        for (Album a : this.albumList) {
            if (a.getName().equals(albumName))
                return true;
        }
        return false;
    }

    /**
     * @author ryantownsend
     * @param object
     * adds album to list
     */
    @Override
    public void add(Album object) {
        if (isDuplicate(object.getName())) return;
        super.add(object);
    }
    /*
    @Override
    public Album getItem(int position) {
        return albumList.get(position);
    }*/

    /**
     * @author ryantownsend
     * @param object
     * @param name
     * @return
     * see if album rename is valid
     */
    public boolean rename(Album object, String name) {
        if (isDuplicate(name)) return false;
        object.setName(name);
        this.notifyDataSetChanged();
        return true;
    }

    /**
     * @author ryantownsend
     * @param position
     * @param convertView
     * @param parent
     * inflater for the items in the list
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(currContext).inflate(R.layout.album_item,parent,false);
        }

        Album a = albumList.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(a.getName());

        return listItem;
    }
}
