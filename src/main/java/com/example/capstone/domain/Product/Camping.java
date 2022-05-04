package com.example.capstone.domain.Product;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "camping")
public class Camping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int campingId;

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

    // 우편주소
    @Column
    private String postalAddress;
    @Column
    private String address;
    // 상세주소
    @Column
    private String detailAddress;

    @CreatedDate
    private String savedTime;
    @LastModifiedDate
    private String modifiedDate;

    // 파일
    @Column()
    private String origFilename;
    @Column()
    private String filename;
    @Column()
    private String filePath;


    // 캠핑장 종류
    @ManyToOne()
    @JoinColumn(name = "infoterid")
    private Infoter infoterId;

    // 판매자 정보
    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;

    public Camping() {}
    public Camping(String campingName, String campingInfo, String campingDetailState, String postalAddress, String address, String detailAddress, String savedTime, String origFilename, String filename, String filePath, Infoter infoterId, Member MID) {
        this.campingName = campingName;
        this.campingInfo = campingInfo;
        this.campingDetailState = campingDetailState;
        this.postalAddress = postalAddress;
        this.address = address;
        this.detailAddress = detailAddress;
        this.savedTime = savedTime;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.infoterId = infoterId;
        this.MID = MID;
    }


    public int getCampingId() {
        return campingId;
    }

    public void setCampingId(int campingId) {
        this.campingId = campingId;
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

    public String getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(String savedTime) {
        this.savedTime =

                savedTime;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
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

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getOrigFilename() {
        return origFilename;
    }

    public void setOrigFilename(String origFilename) {
        this.origFilename = origFilename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Member getMID() {
        return MID;
    }

    public void setMID(Member MID) {
        this.MID = MID;
    }
}
