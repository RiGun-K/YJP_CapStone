package com.example.capstone.domain.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long imageId;

    @JsonBackReference      // arrayList 연결 시 필수 !
    @ManyToOne()
    @JoinColumn(name = "buyId")
    private MenuBuy buyId;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "rentalId")
    private MenuRental rentalId;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "campingId")
    private Camping campingId;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "detailId")
    private CampingDetail detailId;

    @Column()
    private String origFilename;
    @Column()
    private String filename;
    @Column()
    private String filePath;

    public Images() {}

    public Images(Long imageId, MenuBuy buyId, MenuRental rentalId, Camping campingId, CampingDetail detailId, String origFilename, String filename, String filePath) {
        this.imageId = imageId;
        this.buyId = buyId;
        this.rentalId = rentalId;
        this.campingId = campingId;
        this.detailId = detailId;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

    public Images(String origFilename, String filename, String filePath, MenuBuy buyId) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.buyId = buyId;
    }

    public Images(String origFilename, String filename, String filePath,  MenuRental rentalId) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.rentalId = rentalId;
    }

    public Images(String origFilename, String filename, String filePath, Camping campingId) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.campingId = campingId;
    }

    public Images(String origFilename, String filename, String filePath, CampingDetail detailId) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.detailId = detailId;
    }


    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public MenuBuy getBuyId() {
        return buyId;
    }

    public void setBuyId(MenuBuy buyId) {
        this.buyId = buyId;
    }

    public MenuRental getRentalId() {
        return rentalId;
    }

    public void setRentalId(MenuRental rentalId) {
        this.rentalId = rentalId;
    }

    public Camping getCampingId() {
        return campingId;
    }

    public void setCampingId(Camping campingId) {
        this.campingId = campingId;
    }

    public CampingDetail getDetailId() {
        return detailId;
    }

    public void setDetailId(CampingDetail detailId) {
        this.detailId = detailId;
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
