package com.example.capstone.domain.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.storage.StorageBox;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@ToString
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

    @Column
    private double latitude;//위도
    @Column
    private double longitude;//경도

    @Column()
    private int campingViews = 0;


    // 캠핑장 종류
    @ManyToOne()
    @JoinColumn(name = "infoterid")
    private Infoter infoterId;

    // 캠핑장 지역
    @ManyToOne()
    @JoinColumn(name = "areaid")
    private CampingArea areaId;

    // 판매자 정보
    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;

    // 현 테이블의 PK를 외래키로 받는 테이블에서 모든 리스트 조회
    // 따로 칼럼 추가되지 않고, List< > 로 뽑아올 수 있음 !!
    @JsonManagedReference // 부모는 자식을 가져 올 수 있음 ( FK 값 )
    @OneToMany(mappedBy = "campingId", cascade = {CascadeType.ALL})
    private List<CampingDetail> campingDetails = new ArrayList<>();


    @JsonManagedReference // 부모는 자식을 가져 올 수 있음 ( FK 값 )
    @OneToMany(mappedBy = "campingId", cascade = {CascadeType.ALL})
    private List<Images> images = new ArrayList<>();


//    @JsonManagedReference // 부모는 자식을 가져 올 수 있음 ( FK 값 )
//    @OneToMany(mappedBy = "camping", cascade = {CascadeType.ALL})
//    private List<OrderMenu> orderMenus = new ArrayList<>();


    public Camping() {}

    // 생성자 여러개 만들수 있음 DTO를 통해 INSERT 할경우
    public Camping(String campingName, String campingInfo, String campingDetailState, String postalAddress, String address, String detailAddress, String savedTime, String origFilename, String filename, String filePath, Infoter infoterId, CampingArea areaId, Member MID, List<CampingDetail> campingDetails, List<Images> images) {
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
        this.areaId = areaId;
        this.MID = MID;
        this.campingDetails = campingDetails;
        this.images = images;
//        this.orderMenus = orderMenus;
    }

    public Camping(String campingName, String campingInfo, String campingDetailState, String postalAddress, String address, String detailAddress, String savedTime, String origFilename, String filename, String filePath, Infoter infoterId, CampingArea areaId, Member MID) {
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
        this.areaId = areaId;
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
        this.savedTime = savedTime;
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

    public CampingArea getAreaId() {
        return areaId;
    }

    public void setAreaId(CampingArea areaId) {
        this.areaId = areaId;
    }

    public List<CampingDetail> getCampingDetails() {
        return campingDetails;
    }

    public void setCampingDetails(List<CampingDetail> campingDetails) {
        this.campingDetails = campingDetails;
    }

    public int getCampingViews() {
        return campingViews;
    }

    public void setCampingViews(int campingViews) {
        this.campingViews = campingViews;
    }
}
