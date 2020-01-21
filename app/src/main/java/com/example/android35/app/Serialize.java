package com.example.android35.app;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize {

    private static String path = null;
    private static String fileName = "user.ser";

    /**
     * serializes multiple objects
     */
    private Serialize() { }

    /**
     * gets the user which has list of albums and photos and all data we need
     * @param username
     * @return
     */
    public static User getSerializedUser(Context c, String username) {

        User user;

        if (!userExists(c, username)) {
            user = new User("user");
            serializeUser(c, user);
        }


        // Deserialization
        try {


            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            user = (User)in.readObject();

            in.close();
            file.close();
            Log.d("SERIALIZE", "serialized from returned " + path);

            return user;

        } catch(IOException ex) {

        } catch(ClassNotFoundException ex) {

        }

        return null;
    }

    /**
     * Serializes data for data persistence after application termination and start
     * @param user
     */
    public static void serializeUser(Context c, User user) {

        try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(file);


            out.writeObject(user);

            out.close();
            file.close();

            Log.d("SERIALIZE", "Serialized at " + path);

        } catch(IOException e) {

        }

    }

    private static boolean userExists(Context c, String username) {
        path = c.getFilesDir().getAbsolutePath() + File.separator + fileName;
        File f = new File(path);

        if (f.exists() == false) {
            try {
                f.createNewFile();

            } catch (IOException e) {

            }
            return false;
        }
        return true;

    }

}

