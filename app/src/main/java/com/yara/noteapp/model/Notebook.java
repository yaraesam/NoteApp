package com.yara.noteapp.model;

import java.util.HashMap;
import java.util.Map;

public class Notebook {

    String id;
    String uid;
    String title;
    String image;

    public Notebook() {
    }

    public Notebook(String id, String uid, String title, String image) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("uid", uid);
        result.put("title", title);
        result.put("image", image);
        return result;
    }
}
