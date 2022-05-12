package com.example.capstone.dto.storage;

import java.time.LocalDateTime;

public class RenewalBox {
    private String userId;
    private String storageName;
    private String boxName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long useBoxCode;

    public RenewalBox() {
    }

    public RenewalBox(String userId, String storageName, String boxName, LocalDateTime startTime, LocalDateTime endTime) {
        this.userId = userId;
        this.storageName = storageName;
        this.boxName = boxName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getUseBoxCode() {
        return useBoxCode;
    }

    public void setUseBoxCode(Long useBoxCode) {
        this.useBoxCode = useBoxCode;
    }
}
