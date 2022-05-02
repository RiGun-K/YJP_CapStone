package com.example.capstone.domain.Product;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "camping")
public class Camping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int campingId;

    @Column
    private int price;
    @Column
    private String campingName;
    @Column
    private String campingInfo;
    @Column
    private String campingState;

    @Column
    private String address;
    @Column
    private String detailAddress;

    @CreatedDate
    private LocalDateTime savedTime;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Camping() {}

    public int getCampingId() {
        return campingId;
    }

    public void setCampingId(int campingId) {
        this.campingId = campingId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCampingName() {
        return campingName;
    }

    public void setCampingName(String campingName) {
        this.campingName = campingName;
    }

    public String getCampingInfo() {
        return campingInfo;
    }

    public void setCampingInfo(String campingInfo) {
        this.campingInfo = campingInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public LocalDateTime getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(LocalDateTime savedTime) {
        this.savedTime = savedTime;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCampingState() {
        return campingState;
    }

    public void setCampingState(String campingState) {
        this.campingState = campingState;
    }
}
