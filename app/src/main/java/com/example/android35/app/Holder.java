package com.example.android35.app;

import android.content.Context;

/**
 * @author jeremytyson ryantownsend
 * class that holds all the current relevant data we need for each page we are on
 */
public class Holder {
    /**
     * holds user
     */
    private static User user = null;
    /**
     * holds current object we are focusing on
     */
    private static Object object = null;
    /**
     * holds current album we are in
     */
    private static Album album = null;

    /**
     * returns the relevant object we need for a page
     */
    public static Object getContent() {
        return object;
    }

    /**
     * sets an object to be sent to the next view
     * @param o
     */
    public static void setContentNext(Object o) {
        object = o;
    }

    /**
     * gets the album that was sent
     * @return
     */
    public static Album getAlbum() {
        return album;
    }

    /**
     * set album to be sent to the next view
     * @param a
     */
    public static void setAlbum(Album a) { album = a; }



    /**
     * Serializes data
     */
    public static void serializeData(Context c) {
        if (user != null) {
            Serialize.serializeUser(c, user);
        }
    }

    /**
     * gets the current user object
     * @return
     */

    public static User getUser() {
        return user;
    }

    /**
     * Sets the user object
     * @param user
     */

    public static void setUser(User user) {
        Holder.user = user;
    }
}
