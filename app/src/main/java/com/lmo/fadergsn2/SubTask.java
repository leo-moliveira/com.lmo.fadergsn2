package com.lmo.fadergsn2;

public class SubTask {
    private String id;
    private String taskId;
    private String title;
    private String desc;
    private boolean archived;

    public SubTask(String id, String taskId, String title, String desc, boolean archived) {
        this.id = id;
        this.taskId = taskId;
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
