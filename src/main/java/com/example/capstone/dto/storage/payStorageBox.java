package com.example.capstone.dto.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class payStorageBox {

    private String userId;
    private long storageBoxCode;
    private LocalDate useStorageStartTime;
    private LocalDate useStorageEndTime;
    private int price;
    private List<Long> item = new ArrayList<Long>();

    public payStorageBox() {
    }

    public payStorageBox(String userId, long storageBoxCode) {
        this.userId = userId;
        this.storageBoxCode = storageBoxCode;
    }

    public payStorageBox(String userId, long storageBoxCode, LocalDate useStorageStartTime, LocalDate useStorageEndTime, int price, List<Long> item) {
        this.userId = userId;
        this.storageBoxCode = storageBoxCode;
        this.useStorageStartTime = useStorageStartTime;
        this.useStorageEndTime = useStorageEndTime;
        this.price = price;
        this.item = item;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getStorageBoxCode() {
        return storageBoxCode;
    }

    public void setStorageBoxCode(long storageBoxCode) {
        this.storageBoxCode = storageBoxCode;
    }

    public LocalDate getUseStorageStartTime() {
        return useStorageStartTime;
    }

    public void setUseStorageStartTime(LocalDate useStorageStartTime) {
        this.useStorageStartTime = useStorageStartTime;
    }

    public LocalDate getUseStorageEndTime() {
        return useStorageEndTime;
    }

    public void setUseStorageEndTime(LocalDate useStorageEndTime) {
        this.useStorageEndTime = useStorageEndTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Long> getItem() {
        return item;
    }

    public void setItem(List<Long> item) {
        this.item = item;
    }
}
