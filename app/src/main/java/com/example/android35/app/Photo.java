package com.example.android35.app;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jeremytyson
 * Photo class with all photo data and tags
 */
public class Photo implements Serializable {
    /**
     * list of tags
     */
    public List<Tag> tags;
    /**
     * caption of photo (filename)
     */
    String caption;
    String dateString;
    // Date date;
    //ImageView imageview;
    //transient ImageView imageview;
    /**
     * path of image
     */
    String path;
    /**
     * stores bitmap of image
     */
    Bitmap bitmap;
    /**
     * uri of image
     */
    String uri;

    /**
     * @author jeremytyson
     * @param uri
     * constructor for photo instance
     */
    public Photo(Uri uri){
        this.uri = uri.toString();
        //  this.caption = caption;
        //  this.tagTypes.put("location", 1);
        // this.tagTypes.put("people", 2);
        //this.tagTypes.put("number of people", 1);
        //this.date = new Date();
        //   this.path = path;
        // this.uri = uri.toString();
        //this.imageview = new ImageView(new Image(this.location));
        this.tags = new ArrayList<Tag>();
    }
    public String getPath() {
        return this.path;
    }

    /**
     * @author jeremytyson
     * getter for list of tags
     */
    public List<Tag> getTags(){return this.tags;}

    //public List<Tag> getTags(){return this.tags;}
    public void setBitmap(Bitmap bitmap){

    }
    public void setCaption(String newCaption) {
        this.caption = newCaption;
    }
    public Bitmap getBitmap(){
        return this.bitmap;
    }
    public String getCaption() {
        return this.caption;
    }

    /**
     * @author jeremytyson
     * getter for uri of photo
     * @return
     */
    public Uri getUri(){
        return Uri.parse(this.uri);
    }


}