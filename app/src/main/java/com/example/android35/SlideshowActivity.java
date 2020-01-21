package com.example.android35;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android35.app.Album;
import com.example.android35.app.Holder;
import com.example.android35.app.Photo;

/**
 * @author jeremytyson ryantownsend
 * activity that handles the slideshow page
 */
public class SlideshowActivity extends AppCompatActivity {
    /**
     * album we are slideshowing through
     */
    Album album;
    /**
     * current photo we are looking at
     */
    Photo p;
    /**
     * image of photo p
     */
    ImageView image;

    Button backButton;
    Button forwardButton;
    /**
     * current index in the list of photos of this album
     */
    int index;

    /**
     * @author jeremytyson ryantownsend
     * @param savedInstanceState
     * links components on the page to variables and sets album and current photo/index
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        album = (Album)Holder.getContent();
        p = album.getPhotos().get(0);
        index =0;
        image = findViewById(R.id.image);

        backButton = findViewById(R.id.backButton);
        forwardButton = findViewById(R.id.forwardButton);

        image.setImageURI(p.getUri());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonPushed();
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardButtonPushed();
            }
        });

    }

    /**
     * @author jeremytyson ryantownsend
     * serializes data on pause
     */
    @Override
    protected void onPause(){
        super.onPause();
        Holder.serializeData(this.getApplicationContext());

    }

    /**
     * @author jeremytyson ryantownsend
     * goes backwards in the slideshow
     */
    protected void backButtonPushed(){
        if(index==0){
            index = album.getPhotos().size()-1;
            p = album.getPhotos().get(index);
            image.setImageURI(p.getUri());
        }else{
            index--;
            p =album.getPhotos().get(index);
            image.setImageURI(p.getUri());

        }
    }
    /**
     * @author jeremytyson ryantownsend
     * goes forwards in the slideshow
     */
    protected void forwardButtonPushed(){
        if(index==  (album.getPhotos().size()-1)){
            index =0;
            p = album.getPhotos().get(index);
            image.setImageURI(p.getUri());
        }else{
            index++;
            p =album.getPhotos().get(index);
            image.setImageURI(p.getUri());

        }
    }

}
