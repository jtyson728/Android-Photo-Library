package com.example.android35;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android35.app.Album;
import com.example.android35.app.Holder;
import com.example.android35.app.Photo;
import com.example.android35.app.TagAdapter;
import com.example.android35.app.Tag;
import com.example.android35.app.User;

import java.util.List;

//import com.example.android35.app.TagAdapter;

/**
 * @author jeremytyson ryantownsend
 * single photo page where you can add tags and move the photo
 */
public class PhotoActivity extends AppCompatActivity {
    /**
     * user field
     */
    User user;
    /**
     * specific photo object we are looking at
     */
    Photo p;
    Album currAlbum;
    /**
     * list of tags for this photo
     */
    List<Tag> tagList;

    ImageView image;
    ListView tagListView;
    Button addButton;
    Button deleteButton;
    Button moveButton;
    TextView photoCaption;
    /**
     * tag adapter instance that does manipulations with the tags
     */
    private TagAdapter adapter;
    int targetPosition;
    private int pos;
    /**
     * int number to denote tag type (location or person)
     */
    int tagType;

    /**
     * @author jeremytyson ryantownsend
     * @param savedInstanceState
     * links all components on the page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openphoto);

        // get user data
        this.user = Holder.getUser();
       p = (Photo)Holder.getContent();
//
//
//        // link xml components
        moveButton = findViewById(R.id.moveButton);
        image = findViewById(R.id.image);
       // p = (Photo)getIntent().getSerializableExtra("Photo");
       // currAlbum = (Album)getIntent().getSerializableExtra("Album");
        image.setImageURI(p.getUri());
        tagListView = findViewById(R.id.tagListView);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);
        photoCaption = findViewById(R.id.photoCaption);

        photoCaption.setText(p.getUri().getLastPathSegment());


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTag();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTag();
            }
        });

    }

    /**
     * @author jeremytyson ryantownsend
     * serializes the data onpause
     */
    @Override
    protected void onPause() {
        super.onPause();
        Holder.serializeData(this.getApplicationContext());
        Log.d("SERIALIZING DATA", "Data serialized successfully.");
    }

    /**
     * @author jeremytyson ryantownsend
     * listens for button clicks and selections, then calls methods accordingly
     */
    @Override
    protected void onStart(){
        super.onStart();
        pos = -1;
        tagList = p.getTags();
        adapter = new TagAdapter(this, tagList);
        tagListView.setAdapter(adapter);
        tagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
        });

        moveButton.setOnClickListener(new View.OnClickListener() {
            List<String> albums = user.getAlbumsWithout(Holder.getAlbum().getName());
            @Override
            public void onClick(View v) {
                movePhoto(albums);
            }
        });
    }

    /**
     * @author jeremytyson
     * removes selected tags from the photos tagList
     */
    private void deleteTag() {
        if(pos < 0) {
            showAlertDialog("Delete Failed", "Please select a tag to delete");
            return;
        }
        else{
            adapter.remove(adapter.getItem(pos));
            if(p.tags.size() == 0){
                pos = -1;
            }
        }
    }

    /**
     * @author jeremytyson ryantownsend
     * Creates a popup to add a tag and then adds it if it's valid
     */
    private void addTag() {
        LayoutInflater layoutInflater = LayoutInflater.from(PhotoActivity.this);
        View promptView = layoutInflater.inflate(R.layout.add_tag, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PhotoActivity.this);
        alertDialogBuilder.setView(promptView);
        Spinner tagSpinner = promptView.findViewById(R.id.tagSpinner);
        String[] options = {"person","location"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tagSpinner.setAdapter(a);
        tagType = tagSpinner.getSelectedItemPosition();
        tagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String tagValue = editText.getText().toString().trim();
                        if (tagValue.length() == 0) {
                            showAlertDialog("Error", "Value not entered");
                            return;
                        } else {

                            if(tagType == 0){
                                adapter.add(new Tag("person",tagValue));
                            } else if(tagType == 1) {
                                adapter.add(new Tag("location",tagValue));
                            }
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
     * @author ryantownsend
     * @param albums
     * creates a popup to select destination, then moves the photo to that album
     */
    private void movePhoto(List<String> albums){
        LayoutInflater layoutInflater = LayoutInflater.from(PhotoActivity.this);
        View promptView = layoutInflater.inflate(R.layout.move_photo_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PhotoActivity.this);
        alertDialogBuilder.setView(promptView);

        Spinner spinner = promptView.findViewById(R.id.albumSpinner);



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, albums);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        targetPosition = spinner.getSelectedItemPosition();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Move", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!user.movePhoto(Holder.getAlbum(), user.getAlbumByName(adapter.getItem(targetPosition)), p)) {
                            Log.d("PHOTO MOVE", "Photo move failed.");
                            dialog.dismiss();
                           showAlertDialog("Failed To Move Photo", "Destination Album already contains this photo.");
                        } else {
                            Log.d("PHOTO MOVE", "Moved photo successfully.");
                            System.out.println("URI: "+ p.getUri());
                            finish();
                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        /*
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Move", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(Album x : user.getAlbums()){
                            if(x.getName().equals(adapter.getItem(targetPosition))){
                                x.addPhoto(p);
                                //remove photo from current album
                                Holder.getAlbum().getPhotos().remove(p);

                            }
                        }

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();*/
    }

    /**
     * @author jeremytyson ryantownsend
     * @param title
     * @param msg
     * generic alert method
     */
    private void showAlertDialog(String title, String msg) {
        AlertDialog a = new AlertDialog.Builder(PhotoActivity.this).create();
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
