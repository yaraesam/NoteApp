package com.yara.noteapp.model;

import java.util.HashMap;
import java.util.Map;

public class Note {

    String id;
    String uid;
    String notebookId;
    String title;
    String desc;
    long createdAt;


    public Note() {
    }

    public Note(String id, String uid, String notebookId, String title, String desc, long createdAt) {
        this.id = id;
        this.uid = uid;
        this.notebookId = notebookId;
        this.title = title;
        this.desc = desc;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("uid", uid);
        result.put("notebookId", notebookId);
        result.put("title", title);
        result.put("desc", desc);
        result.put("createdAt", createdAt);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", notebookId='" + notebookId + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
