package com.lmo.fadergsn2;

public class Task {
    private String id;
    private String userId;
    private String title;
    private String desc;
    private boolean archived;

    public Task(String id, String userId, String title, String desc, boolean archived) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.archived = archived;
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
