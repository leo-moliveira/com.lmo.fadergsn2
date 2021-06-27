package com.lmo.fadergsn2;

import java.util.Date;

public class SubTask {
    private String id;
    private String taskId;
    private String title;
    private String desc;
    private Date createdAt;
    private boolean completed;
    public SubTask(){}
    public SubTask(String id, String taskId, String title, String desc, boolean archived) {
        this.id = id;
        this.taskId = taskId;
        this.title = title;
        this.desc = desc;
        this.completed = archived;
    }

    public boolean isArchived() {
        return completed;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setArchived(boolean archived) {
        this.completed = archived;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
