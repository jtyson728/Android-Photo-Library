package com.example.android35;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.android35.app.Album;
import com.example.android35.app.AlbumAdapter;
import com.example.android35.app.Holder;
import com.example.android35.app.Serialize;
import com.example.android35.app.User;

import java.util.List;

/**
 * @author jeremytyson Ryan Townsend
 * home page that displays list of albums
 */
public class MainActivity extends AppCompatActivity {
    /**
     * user object that contains list of albums
     */
    User user = null;
    Context context;
    Button openButton;
    Button createButton;
    Button renameButton;
    Button deleteButton;
    Button searchButton;
    ListView albumListView;
    /**
     * string value of selected item in listview
     */
    String selection;
    /**
     * position of selected item in listview
     */
    int pos;
    /**
     * adapter that we use to go into an album
     */
    private AlbumAdapter adapter;

    /**
     * @author jeremytyson ryantownsend
     * @param savedInstanceState
     * sets up our album list page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the user here
       if (user == null) {
          this.context = this.getApplicationContext();
          user = Serialize.getSerializedUser(this.getApplicationContext(), "user");
            Holder.setUser(user);
        }

        //link xml parts
        albumListView = findViewById(R.id.albumListView);
        openButton = findViewById(R.id.moveButton);
        renameButton = findViewById(R.id.renameButton);
        createButton = findViewById(R.id.createButton);
        searchButton = findViewById(R.id.searchBothButton);
        deleteButton = findViewById(R.id.deleteButton);

    }

    /**
     * @author ryantownsend
     * Serialize data onpause
     */
    @Override
    protected void onPause() {
        super.onPause();

        Holder.serializeData(this.getApplicationContext());
        Log.d("SERIALIZING DATA", "Data serialized successfully.");
    }

    /**
     * @author ryantownsend
     * @param requestCode
     * @param permissions
     * @param grantResults
     * deals with permissions to see if user can do something
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Permissions Denied");
            alertDialog.setMessage("Permissions Denied");
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }

    }


    /**
     * @author jeremytyson ryantownsend
     * Listens for button pushes and directs to methods that handle them, and sets and gets context
     */
    @Override
    protected void onStart(){
        super.onStart();
        pos = -1;
        selection = null;

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                1);
        if (user == null) {
            this.context = this.getApplicationContext();

            user = Serialize.getSerializedUser(this.getApplicationContext(),"user");
            Holder.setUser(user);
        }

        List<Album> albumList = user.getAlbums();
        adapter = new  AlbumAdapter(this , albumList);
        albumListView.setAdapter(adapter);

        //set listeners for buttons
        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selection = String.valueOf(parent.getItemAtPosition(position));
                pos = position;
            }
        });


        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selection == null) {
                    showAlertDialog("Open Failed", "No Album Selected");
                } else {
                    openAlbum();
                }
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlbum();
            }
        });



        renameButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selection == null){
                    showAlertDialog("Rename Failed", "No Album Selected");
                } else {

                    renameAlbum();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selection == null) {
                    showAlertDialog("Deletion Failed", "No Album Selected");
                } else {
                    adapter.remove(adapter.getItem(pos));
                    albumListView.clearChoices();
                    adapter.notifyDataSetChanged();
                    Serialize.serializeUser(getApplicationContext(),user);
                    albumListView.setAdapter(adapter);

                        pos = -1;

                        selection = null;

                }
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openSearch();
            }
        });
    }

    /**
     * @author jeremytyson ryantownsend
     * opens the search page
     */
    protected void openSearch(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @author jeremytyson ryantownsend
     * opens the single album view page with all the photos
     */
    public void openAlbum(){
        Intent intent = new Intent(this, OpenAlbumActivity.class);
        Holder.setContentNext(adapter.getItem(pos));
        //intent.putExtra("Album", adapter.getItem(pos));
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
       // intent.putExtra("User", user);
        startActivity(intent);

    }

    /**
     * @author jeremytyson ryantownsend
     * see if new name is valid then renames the album
     */
    protected void renameAlbum(){
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.album_input_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String newName = editText.getText().toString().trim();
                        if(newName.length() == 0){
                            showAlertDialog("Rename Failed", "Invalid Name");
                            dialog.dismiss();
                        } else {
                            if(!adapter.rename(adapter.getItem(pos), newName)){
                                showAlertDialog("Rename Failed", "Duplicate Album Name");
                            }
                            Serialize.serializeUser(getApplicationContext(),user);
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    /**
     * @author jeremytyson ryantownsend
     * enter name and create new album
     */
    protected void createAlbum() {
        System.out.println("Create Button Pushed");
        Log.d("created album", "album created");
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View alertView = inflater.inflate(R.layout.album_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(alertView);

        final EditText editText = alertView.findViewById(R.id.edittext);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = editText.getText().toString().trim();
                        if (name.length() == 0) {
                            showAlertDialog("Create Failed", "Invalid Name");
                            dialog.dismiss();
                        } else {

                                adapter.add(new Album(name));

                            Serialize.serializeUser(getApplicationContext(),user);
                        }

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    /**
     * @author jeremytyson ryantownsend
     * @param title
     * @param msg
     * generic show alert method that we have everywhere for every alert
     */
    private void showAlertDialog(String title, String msg) {
        AlertDialog a = new AlertDialog.Builder(MainActivity.this).create();
        a.setTitle(title);
        a.setMessage(msg);
        a.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        a.show();
    }

}
