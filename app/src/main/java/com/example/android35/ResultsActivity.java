package com.example.android35;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android35.app.Holder;
import com.example.android35.app.Photo;
import com.example.android35.app.PhotoAdapter;

import java.util.List;

/**
 * @author ryantownsend
 * activity that displays the searchResults list of photos on a new page
 */
public class ResultsActivity extends AppCompatActivity {
    /**
     * list of photos from search results
     */
    List<Photo> photos;
    /**
     * instance of photo adapter to populate gridview
     */
    PhotoAdapter adapter;
    /**
     * gridview component that displays results
     */
    GridView photoGrid;
    TextView textView2;

    /**
     * @author ryantownsend
     * @param savedInstanceState
     * photos gets assigned to the searchResults list and photogrid gets linked
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // get user data
        photos = (List<Photo>) Holder.getContent();

        // pair XML components
        photoGrid = findViewById(R.id.photoGrid);
        textView2 = findViewById(R.id.textView2);
        textView2.setText("Search Results");
    }

    /**
     * @author ryantownsend
     * populates the gridview with results
     */
    @Override
    protected void onStart() {
        super.onStart();

        adapter = new PhotoAdapter(this,photos);
        photoGrid.setAdapter(adapter);
    }
}
