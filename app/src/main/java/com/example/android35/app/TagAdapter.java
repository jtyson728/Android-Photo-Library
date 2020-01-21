package com.example.android35.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.android35.R;

/**
 * @author jeremytyson ryantownsend
 * adapter to correctly display tags for a photo
 */
public class TagAdapter extends ArrayAdapter<Tag> {
    /**
     * context we are currently in
     */
    private Context context;
    /**
     * list of tags we are using
     */
    private List<Tag> tagList = new ArrayList<Tag>();

    /**
     * @author jeremytyson ryantownsend
     * @param context
     * @param list
     * constructor for new tag adapter instance
     */
    public TagAdapter(Context context, List<Tag> list) {
        super(context , 0, list);
        this.context = context;
        this.tagList = list;
    }

    /**
     * @author jeremytyson ryantownsend
     * @param tag
     * @return
     * see if a tag is a duplicate
     */
    private boolean isDuplicate(Tag tag) {
        for (Tag i : tagList) {
            if (i.getTagType().equals(tag.getTagType()) && i.getTagValue().equals(tag.getTagValue()))
                return true;
        }
        return false;
    }

    /**
     * @author jeremytyson ryantownsend
     * @param object
     * remove tag from list
     */
    @Override
    public void remove(Tag object) {
        super.remove(object);
    }

    /**
     * @author jeremytyson ryantownsend
     * @param object
     * add a tag to the list
     */
    @Override
    public void add(Tag object) {
        if (isDuplicate(object)) return;//
        super.add(object);
    }

    /**
     * @author jeremytyson ryantownsend
     * @param position
     * @param convertView
     * @param parent
     * @return
     * inflater to correctly display tag items in the listview
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.tag_entry,parent,false);
        }

        Tag a = tagList.get(position);
        TextView name = (TextView) listItem.findViewById(R.id.tagTextView);
        name.setText(a.getTagType() + " : " + a.getTagValue());

        return listItem;
    }
}
