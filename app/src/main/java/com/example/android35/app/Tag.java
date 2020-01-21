package com.example.android35.app;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.io.FileInputStream;
import java.util.*;

/**
 * @author jeremytyson ryantownsend
 * Tag class that holds all tag information
 */
public class Tag implements Serializable {
    /**
     * type of tag location or person
     */
    String tagType;
    /**
     * value of tag
     */
    String tagValue;

    /**
     * @author jeremytyson ryantownsend
     * @param tagType
     * @param tagValue
     * constructor for tag
     */
    public Tag(String tagType, String tagValue) {
        this.tagType = tagType;
        this.tagValue = tagValue;
    }

    /**
     * @author jeremytyson ryantownsend
     * @return
     * getter for tag type
     */
    public String getTagType() {
        return this.tagType;
    }

    /**
     * @author jeremytyson ryantownsend
     * @return
     * getter for tag value
     */
    public String getTagValue() {
        return this.tagValue;
    }

    /**
     * @author jeremytyson ryantownsend
     * @return
     * override to string method
     */
    public String toString() {
        return this.tagType + "                    " + this.tagValue;
    }
}

