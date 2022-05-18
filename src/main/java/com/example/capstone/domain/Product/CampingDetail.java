package com.example.capstone.domain.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    // 차후 객실 상태도 추가해야함 ! ( state { 0 : 비어있음, 1 : 마감 } )


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
    private String savedTime;

    // 캠핑장아이디
    @JsonBackReference  // 자식은 부모를 가져 올 수 없음 ( FK 값 )
    @ManyToOne()
    @JoinColumn(name = "campingId")
    private Camping campingId;

    @JsonManagedReference // 부모는 자식을 가져 올 수 있음 ( FK 값 )
    @OneToMany(mappedBy = "detailId",cascade = {CascadeType.ALL})
    private List<Images> images = new ArrayList<>();

    public CampingDetail() {}
    public CampingDetail(String detailName, int detailPrice, Integer baseNumber, Integer maximumNumber, String detailFunction, String savedTime, String origFilename, String filename, String filePath, Camping campingId, List<Images> images) {
        this.detailName = detailName;
        this.detailPrice = detailPrice;
        this.baseNumber = baseNumber;
        this.maximumNumber = maximumNumber;
        this.detailFunction =detailFunction;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.savedTime = savedTime;
        this.campingId = campingId;
        this.images = images;
    }

    public CampingDetail(String detailName, int detailPrice, Integer baseNumber, Integer maximumNumber, String detailFunction, String savedTime, String origFilename, String filename, String filePath, Camping campingId) {
        this.detailName = detailName;
        this.detailPrice = detailPrice;
        this.baseNumber = baseNumber;
        this.maximumNumber = maximumNumber;
        this.detailFunction =detailFunction;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.savedTime = savedTime;
        this.campingId = campingId;
    }

    public String getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(String savedTime) {
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
