package com.example.capstone.dto.storage;

import java.time.LocalDate;

public class RenewalBox {
    private String userId;
    private String storageName;
    private String boxName;
    private LocalDate startTime;
    private LocalDate endTime;
    private Long useBoxCode;

    public RenewalBox() {
    }

    public RenewalBox(String userId, String storageName, String boxName, LocalDate startTime, LocalDate endTime) {
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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Long getUseBoxCode() {
        return useBoxCode;
    }

    public void setUseBoxCode(Long useBoxCode) {
        this.useBoxCode = useBoxCode;
    }
}
