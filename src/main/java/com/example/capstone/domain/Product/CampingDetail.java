package com.example.capstone.domain.Product;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="campingdetail")
public class CampingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int detailId;

    @Column
    private String detailName;

    // 객실기능 ( 미온수수영장/제트온풀욕조/찜질 , 22년신축,개별화장실,개별바비큐장 )
    @Column
    private String detailFunction;
    // 객실설명 ( 상세설명 , 차후 PDF 로 변경예정 )
    @Column
    private String detailEx;
    // 객실마다 가격설정
    @Column
    private int detailPrice;


    // 기준인원
    @Column
    private Integer baseNumber;
    // 최대인원
    @Column
    private Integer maximumNumber;

    // 파일
    @Column()
    private String origFilename;
    @Column()
    private String filename;
    @Column()
    private String filePath;

    @CreatedDate
    private LocalDate savedTime;

    // 캠핑장아이디
    @ManyToOne()
    @JoinColumn(name = "campingId")
    private Camping campingId;

    public CampingDetail() {}
    public CampingDetail(String detailName, String detailEx, Integer baseNumber, Integer maximumNumber, LocalDate savedTime, String origFilename, String filename, String filePath, Camping campingId) {
        this.detailName = detailName;
        this.detailEx = detailEx;
        this.baseNumber = baseNumber;
        this.maximumNumber = maximumNumber;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.savedTime = savedTime;
        this.campingId = campingId;
    }


    public LocalDate getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(LocalDate savedTime) {
        this.savedTime = savedTime;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getDetailFunction() {
        return detailFunction;
    }

    public void setDetailFunction(String detailFunction) {
        this.detailFunction = detailFunction;
    }

    public String getDetailEx() {
        return detailEx;
    }

    public void setDetailEx(String detailEx) {
        this.detailEx = detailEx;
    }

    public Integer getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(Integer baseNumber) {
        this.baseNumber = baseNumber;
    }

    public Integer getMaximumNumber() {
        return maximumNumber;
    }

    public void setMaximumNumber(Integer maximumNumber) {
        this.maximumNumber = maximumNumber;
    }

    public Camping getCampingId() {
        return campingId;
    }

    public void setCampingId(Camping campingId) {
        this.campingId = campingId;
    }

    public int getDetailPrice() {
        return detailPrice;
    }

    public void setDetailPrice(int detailPrice) {
        this.detailPrice = detailPrice;
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
}
