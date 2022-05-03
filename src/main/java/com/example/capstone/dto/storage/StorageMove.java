package com.example.capstone.dto.storage;

public class StorageMove {

    private String userId;
    private long use;
    private long before;
    private long after;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getUse() {
        return use;
    }

    public void setUse(long use) {
        this.use = use;
    }

    public long getBefore() {
        return before;
    }

    public void setBefore(long before) {
        this.before = before;
    }

    public long getAfter() {
        return after;
    }

    public void setAfter(long after) {
        this.after = after;
    }
}
