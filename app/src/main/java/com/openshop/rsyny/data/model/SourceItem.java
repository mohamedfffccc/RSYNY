package com.openshop.rsyny.data.model;

public class SourceItem {
    public int image;
    public String name;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceItem(int image, String name) {
        this.image = image;
        this.name = name;
    }
}
