package com.lmo.fadergsn2;

import com.google.gson.Gson;
import com.google.type.DateTime;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Task {
    private String id;
    private String userId;
    private String title;
    private String desc;
    private Date createdAt;
    private boolean archived;

    public Task(){}
    public Task(String id, String userId, String title, String desc, Date createdAt,boolean archived) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.createdAt = createdAt;
        this.archived = archived;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

}
