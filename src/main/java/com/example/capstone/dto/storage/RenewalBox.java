package com.example.capstone.dto.storage;

import java.time.LocalDate;

public class RenewalBox {
    private LocalDate startTime;
    private LocalDate endTime;
    private Long useBoxCode;
    private int price;

    public RenewalBox() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
