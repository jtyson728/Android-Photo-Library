package com.example.android35.app;

import java.util.*;
import java.io.Serializable;
/**
 * @author jeremytyson
 * User object class that keeps track of its albums
 */
public class User implements Serializable {
    /**
     * Stores this users username
     */
    String username;
    /**
     *  list of albums that this user has
     */
    List<Album> AlbumList;
    /**
     * @author jeremytyson
     * @param username
     * constructor for user object
     */
    public User(String username) {
        this.username = username;
        this.AlbumList = new ArrayList<Album>();
    }
    /**
     * Getter for username
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * Override toString to display username properly
     */
    public String toString() {
        return this.username;

    }
    /**
     * Getter for album list
     */
    public List<Album> getAlbums(){
        return this.AlbumList;
    }



    public Album getAlbumByName(String name) {
        for (Album a : this.AlbumList) {
            if (a.getName().equals(name)){
                return a;
            }
        }
        return null;
    }

    public List<String> getAlbumsWithout(String album){
        List<String> ret = new ArrayList<>();

        for (Album a : this.AlbumList) {
            if (a.getName().equals(album)) continue;
            ret.add(a.getName());
        }
        return ret;
    }
    public boolean movePhoto(Album source, Album target, Photo photo) {
        if (isDuplicate(target, photo)) return false;

        source.getPhotos().remove(photo);
        target.getPhotos().add(photo);
        return true;
    }

    private boolean isDuplicate(Album target, Photo photo) {
        for (Photo p : target.getPhotos()) {
            if (p.getUri().equals(photo.getUri()))
                return true;
        }

        return false;
    }

}
