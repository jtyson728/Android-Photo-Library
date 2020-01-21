package com.example.android35;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android35.app.Album;
import com.example.android35.app.Holder;
import com.example.android35.app.Photo;
import com.example.android35.app.PhotoAdapter;
import com.example.android35.app.Serialize;
import com.example.android35.app.User;

import java.io.IOException;
import java.util.List;

//import android.support.v7.app.AppCompatActivity;

/**
 * @author Jeremy Tyson
 * class that sets up the open album page
 */
public class OpenAlbumActivity extends AppCompatActivity {
    /**
     * user field
     */
    User user;
    /**
     * specific album we are looking at
     */
    Album album;
    GridView photoList;
    /**
     * selection position in gridview
     */
    private int pos;
    /**
     * list of photos
     */
    List<Photo> photos;
    PhotoAdapter photoAdapter;
    TextView albumName;
    Button deleteButton;
    Button openButton;
    Button addButton;
    Button slideshowButton;
    /**
     * string value of selection in gridview
     */
    String selection;
    /**
     * gallery selection field
     */
    public static final int PHOTO_FROM_GALLERY =1;

    /**
     * @author jeremytyson
     * @param savedInstanceState
     * links all the components on the page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        this.user = Holder.getUser();
        album = (Album)Holder.getContent();
        //album = (Album)getIntent().getSerializableExtra("Album");
        albumName = findViewById(R.id.photoCaption);
        photoList = findViewById(R.id.photoGridView);
        openButton = findViewById(R.id.moveButton);
        deleteButton = findViewById(R.id.deleteButton);
        addButton = findViewById(R.id.addButton);
        slideshowButton = findViewById(R.id.slideshowButton);
        albumName.setText(album.getName());
    }

    /**
     * @author jeremytyson
     * serialize data onpause
     */
    @Override
    protected void onPause() {
        super.onPause();
        Holder.serializeData(this.getApplicationContext());

    }

    /**
     * @author jeremytyson
     * listens for button pushes
     */
    @Override
    protected void onStart() {
        super.onStart();
        pos = -1;
        selection = null;
        this.photos = album.getPhotos();

        photoAdapter = new PhotoAdapter(this,this.photos);
        photoList.setAdapter(photoAdapter);

        photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Holder.setContentNext(photoList.getSelectedItem());


                pos = position;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if( pos < 0) {
                    showAlertDialog("Delete Failed", "No photo selected.");
                } else {
                    photoAdapter.remove(photoAdapter.getItem(pos));
                    photoAdapter.notifyDataSetChanged();
                    Serialize.serializeUser(getApplicationContext(),user);

                }

                    pos = -1;

            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos < 0) {

                    showAlertDialog("Open Failed", "No photo selected.");
                } else {
                    openPhoto();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
                photoAdapter.notifyDataSetChanged();


                    photoList.setSelection(0);
            }
        });
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSlideShow();
            }
        });
    }

    /**
     * @author jeremytyson
     * opens the slideshow page
     */
    private void startSlideShow(){
        if(album.getPhotos().size() != 0 ) {
            Intent intent = new Intent(this, SlideshowActivity.class);
            Holder.setContentNext(album);
            startActivity(intent);
        }else{
            showAlertDialog("Slideshow Failure", "Cannot create slideshow, album is empty.");
        }
    }

    /**
     * @author jeremytyson
     * @param title
     * @param msg
     * generic method to setup alert
     */
    private void showAlertDialog(String title, String msg) {
        AlertDialog a = new AlertDialog.Builder(OpenAlbumActivity.this).create();
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

    /**
     * @author jeremytyson
     * opens a single photo and switches to photo activity
     */
    public void openPhoto() {

        Intent intent = new Intent(this, PhotoActivity.class);
        //intent.putExtra("Photo",photoAdapter.getItem(pos));
        //intent.putExtra("Album",album);

        Photo p = photoAdapter.getItem(pos);

        Holder.setContentNext(p);
        Holder.setAlbum(album);
        startActivity(intent);
    }

    /**
     * @author jeremytyson ryantownsend
     * switches view to allow user to pick image from gallery
     */
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_FROM_GALLERY);
    }

    /**
     * @author jeremytyson
     * @param requestCode
     * @param resultCode
     * @param data
     * when user selects image to add, this adds the path and serializes
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if(data == null){
            return;

        }*/
        if(requestCode == PHOTO_FROM_GALLERY && resultCode == Activity.RESULT_OK) {


            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Photo p = new Photo(uri);
                PhotoAdapter adapter = (PhotoAdapter) photoList.getAdapter();
                //possible check for duplicate
                if (!adapter.addPhoto(p)) {
                    showAlertDialog("Add Failed", "Album already contains this photo.");
                }

                Serialize.serializeUser(this.getApplicationContext(), user);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}