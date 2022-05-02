package com.example.capstone.domain.Product;

import javax.persistence.*;

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

    // 기준인원
    @Column
    private Integer baseNumber;
    // 최대인원
    @Column
    private Integer maximumNumber;

    // 캠핑장아이디
    @ManyToOne()
    @JoinColumn(name = "campingId")
    private Camping campingId;

    public CampingDetail() {}
    public CampingDetail(int detailId, String detailName, String detailEx, Integer baseNumber, Integer maximumNumber, Camping campingId) {
        this.detailId = detailId;
        this.detailName = detailName;
        this.detailEx = detailEx;
        this.baseNumber = baseNumber;
        this.maximumNumber = maximumNumber;
        this.campingId = campingId;
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
}
