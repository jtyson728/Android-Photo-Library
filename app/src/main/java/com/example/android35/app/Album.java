package com.example.android35.app;

import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Date;

/**
 * @author jeremytyson ryantownsend
 * album class similar to our album object from last project
 */
public class Album implements Serializable{
    /**
     * Name of album
     */
    String albumName;
    /**
     * Keeps count of how many photos are in album
     */
    int numPhotos;

    //Date earliestDate;

   // Date latestDate;
    /**
     * The list of photo objects in this album
     */
    ArrayList<Photo> photos;
    /**
     * @param albumName
     * @author Ryan Townsend
     * constructor for album object
     */
    public Album(String albumName) {
        this.albumName = albumName;
        this.numPhotos =0;
        //this.earliestDate=0;
        //this.latestDate =0;
        this.photos = new ArrayList<Photo>();
    }
    /**
     * @param newName
     * setter for album name
     */
    public void setName(String newName) {
        this.albumName = newName;
    }
    /**
     * getter for album name
     */
    public String getName() {
        return this.albumName;
    }
    public int getNumPhotos() {
        return this.numPhotos;
    }


    /**
     * getter for list of photos
     */
    public ArrayList<Photo> getPhotos(){
        return this.photos;
    }

    public void addPhoto(Photo newPhoto) {
        this.photos.add(newPhoto);
        this.numPhotos++;
    }
    public void removePhoto(Photo newPhoto) {
        this.photos.remove(newPhoto);
        this.numPhotos--;
    }
}