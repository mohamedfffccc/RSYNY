package com.openshop.rsyny.data.local.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity

public class NewItem {
    @PrimaryKey(autoGenerate = true)


    int id;
    String image;
    String source_name;
    String title;
    String new_url;

    public NewItem(String image, String source_name, String title, String new_url) {
        this.image = image;
        this.source_name = source_name;
        this.title = title;
        this.new_url = new_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNew_url() {
        return new_url;
    }

    public void setNew_url(String new_url) {
        this.new_url = new_url;
    }
}
