package com.example.android35;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android35.app.Album;
import com.example.android35.app.Holder;
import com.example.android35.app.User;
import com.example.android35.app.Photo;
import com.example.android35.app.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jeremytyson ryantownsend
 * activity that lets user input filters for search and search on those filters
 */
public class SearchActivity extends AppCompatActivity {
    /**
     * first tag type to search on
     */
    int tagType1;
    /**
     * first tag type turned into string version
     */
    String tagTypeString1;
    /**
     * second tag type to search on
     */
    int tagType2;
    /**
     * second tag type turned into string version
     */
    String tagTypeString2;
    /**
     * first tag value entered
     */
    String tagValue1;
    /**
     * second tag value entered
     */
    String tagValue2;
    /**
     * stores if user is searching with and/or
     */
    int searchType;
    /**
     * current user
     */
    User user;
    /**
     * list that stores the results
     */
    List<Photo> searchResults;
    /**
     * variable to look at the value we are comparing the filters to
     */
    String compareValue;
    /**
     * variable to look at the type we are comparing the filters to
     */
    String compareType;


    Spinner typeSpinner1;
    Spinner typeSpinner2;
    Spinner andOrSpinner;
    EditText valueField1;
    EditText valueField2;
    Button searchFirstButton;
    Button searchSecondButton;
    Button searchBothButton;

    /**
     * @author jeremytyson ryantownsend
     * @param savedInstanceState
     * links all the components on the page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        user = Holder.getUser();

        typeSpinner1 = findViewById(R.id.typeSpinner1);
        typeSpinner2 = findViewById(R.id.typeSpinner2);
        andOrSpinner = findViewById(R.id.andOrSpinner);
        valueField1 = findViewById(R.id.valueField1);
        valueField2 = findViewById(R.id.valueField2);
        searchBothButton = findViewById(R.id.searchBothButton);
        searchFirstButton = findViewById(R.id.searchFirstButton);
        searchSecondButton = findViewById(R.id.searchSecondButton);
        searchResults = new ArrayList<Photo>();





    }

    /**
     * @author jeremytyson ryantownsend
     * sets all the drop down spinners to display correct items and listens for button pushes
     */
    @Override
    protected void onStart(){
        super.onStart();

        searchResults.clear();

        String[] tagTypes = {"Person","Location"};

        ArrayAdapter<String> adapter1 =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tagTypes);
        typeSpinner1.setAdapter(adapter1);
        typeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagType1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapter2 =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tagTypes);
        typeSpinner2.setAdapter(adapter2);
        typeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagType2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] andOr = {"AND","OR"};
        ArrayAdapter<String> andOrAdapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,andOr);
        andOrSpinner.setAdapter(andOrAdapter);
        andOrSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchFirstButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @author ryantownsend
             * @param v
             * searches on first filter
             */
            @Override
            public void onClick(View v) {
                if(tagType1 == 0){
                    tagTypeString1 = "person";
                }
                else if(tagType1 == 1){
                    tagTypeString1 = "location";
                }
                tagValue1 = valueField1.getText().toString().trim();
                if(tagValue1.length() < 1){
                    showAlertDialog("No Tags Specified", "Please enter at least 1 tag");
                    return;
                }
                else{
                    for(Album i : user.getAlbums()){
                        for(Photo j : i.getPhotos()){
                            for(Tag k : j.getTags()){
                                compareValue = k.getTagValue().toLowerCase();
                                compareType = k.getTagType().toLowerCase();
                                if(compareType.equals(tagTypeString1) && tagValue1.equals(compareValue.substring(0, tagValue1.length()))){
                                    searchResults.add(j);
                                    break;
                                }
                            }
                        }
                    }
                }
                Holder.setContentNext(searchResults);
                openSearchResults();

            }
        });

        searchSecondButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @author ryantownsend
             * @param v
             * searches on 2nd filter
             */
            @Override
            public void onClick(View v) {
                if(tagType2 == 0){
                    tagTypeString2 = "person";
                }
                else if(tagType2 == 1){
                    tagTypeString2 = "location";
                }
                tagValue2 = valueField2.getText().toString().trim();
                if(tagValue2.length() < 1){
                    showAlertDialog("No Tags Specified", "Please enter at least 1 tag");
                    return;
                }
                else{
                    for(Album i : user.getAlbums()){
                        for(Photo j : i.getPhotos()){
                            for(Tag k : j.getTags()){
                                compareValue = k.getTagValue().toLowerCase();
                                compareType = k.getTagType().toLowerCase();
                                if(compareType.equals(tagTypeString2) && tagValue2.equals(compareValue.substring(0, tagValue2.length()))){
                                    searchResults.add(j);
                                    break;
                                }
                            }
                        }
                    }
                }
                Holder.setContentNext(searchResults);
                openSearchResults();

            }
        });

        searchBothButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @author jeremytyson
             * @param v
             * searches on both filters and sees and/or
             */
            @Override
            public void onClick(View v) {

                if(tagType2 == 0){
                    tagTypeString2 = "person";
                }
                else if(tagType2 == 1){
                    tagTypeString2 = "location";
                }

                if(tagType1 == 0){
                    tagTypeString1 = "person";
                }
                else if(tagType1 == 1){
                    tagTypeString1 = "location";
                }

                tagValue1 = valueField1.getText().toString().trim();
                if(tagValue1.length() < 1){
                    showAlertDialog("No Tags Specified", "Please enter at least 1 tag");
                    return;
                }

                tagValue2 = valueField2.getText().toString().trim();
                if(tagValue2.length() < 1){
                    showAlertDialog("No Tags Specified", "Please enter at least 1 tag");
                    return;
                }
                else{
                    if(searchType == 0){
                        for(Album i : user.getAlbums()) {
                            for(Photo x : i.getPhotos()) {
                                if((x.tags.stream().filter(o -> ((o.getTagType().equals(tagTypeString1)
                                        && tagValue1.equals(o.getTagValue().substring(0, tagValue1.length()))))).findFirst().isPresent())
                                        && (x.tags.stream().filter(o -> (o.getTagType().equals(tagTypeString2) && tagValue2.equals(o.getTagValue().substring(0, tagValue2.length())))).findFirst().isPresent())) {
                                    searchResults.add(x);
                                }
                            }
                        }
                    }
                    else if (searchType == 1){
                        for(Album i : user.getAlbums()) {
                            for(Photo x : i.getPhotos()) {
                                if(x.tags.stream().filter(o -> ((o.getTagType().equals(tagTypeString1)
                                        && tagValue1.equals(o.getTagValue().substring(0, tagValue1.length())))
                                        || (o.getTagType().equals(tagTypeString2) && tagValue2.equals(o.getTagValue().substring(0, tagValue2.length()))))).findFirst().isPresent()){
                                    searchResults.add(x);
                                }
                            }
                        }
                    }
                }
                Holder.setContentNext(searchResults);
                openSearchResults();

            }
        });
    }

    /**
     * @author jeremytyson ryantownsend
     * switches view to the results page
     */
    public void openSearchResults() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    /**
     * @author jeremytyson ryantownsend
     * @param title
     * @param msg
     * generic alert method
     */
    private void showAlertDialog(String title, String msg) {
        AlertDialog a = new AlertDialog.Builder(SearchActivity.this).create();
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