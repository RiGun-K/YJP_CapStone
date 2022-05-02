package com.example.capstone.domain.Product;

import com.example.capstone.domain.Member.Member;
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
    // 캠핑장 정보 ( 한옥펜션과 수영장이 있는 카라반캠핑장 )
    @Column
    private String campingInfo;

    // 캠핑장 예약 상태 ( 0 = 마감, 1 = 운영, 2 = 기타 )
    @Column(length = 1)
    private String campingState;
    // 가능 객실 수 
    @Column(length = 2)
    private String campingDetailState;

    @Column
    private String address;
    @Column
    private String detailAddress;

    @CreatedDate
    private LocalDate savedTime;
    @LastModifiedDate
    private LocalDate modifiedDate;


    // 캠핑장 종류
    @ManyToOne()
    @JoinColumn(name = "infoterId")
    private Infoter infoterId;

    // 판매자 정보
    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;

    public Camping() {}
    public Camping(int campingId, int price, String campingName, String campingInfo, String campingState, String campingDetailState, String address, String detailAddress, LocalDate savedTime, LocalDate modifiedDate, Infoter infoterId) {
        this.campingId = campingId;
        this.price = price;
        this.campingName = campingName;
        this.campingInfo = campingInfo;
        this.campingState = campingState;
        this.campingDetailState = campingDetailState;
        this.address = address;
        this.detailAddress = detailAddress;
        this.savedTime = savedTime;
        this.modifiedDate = modifiedDate;
        this.infoterId = infoterId;
    }

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

    public LocalDate getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(LocalDate savedTime) {
        this.savedTime = savedTime;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Infoter getInfoterId() {
        return infoterId;
    }

    public void setInfoterId(Infoter infoterId) {
        this.infoterId = infoterId;
    }

    public String getCampingState() {
        return campingState;
    }

    public void setCampingState(String campingState) {
        this.campingState = campingState;
    }

    public String getCampingDetailState() {
        return campingDetailState;
    }

    public void setCampingDetailState(String campingDetailState) {
        this.campingDetailState = campingDetailState;
    }
}
