package com.example.capstone.domain.Product;

import com.example.capstone.domain.Member.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="menubuy")
public class MenuBuy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int buyId;

    @Column()
    private String buyName;
    @Column()
    private int buyPrice;
    @Column()
    private String buyEx;
    @Column()
    private int buyStock;

    // 파일
    @Column()
    private String origFilename;
    @Column()
    private String filename;
    @Column()
    private String filePath;

    @CreatedDate
    private LocalDate savedTime;
    @LastModifiedDate
    private LocalDate modifiedDate;

    // 구매종류
    @ManyToOne()
    @JoinColumn(name = "kindid")
    private Kind kindid;

    // 판매자 정보
    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;

    public MenuBuy() {}


    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBuyEx() {
        return buyEx;
    }

    public void setBuyEx(String buyEx) {
        this.buyEx = buyEx;
    }

    public int getBuyStock() {
        return buyStock;
    }

    public void setBuyStock(int buyStock) {
        this.buyStock = buyStock;
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

    public Kind getKindid() {
        return kindid;
    }

    public void setKindid(Kind kindid) {
        this.kindid = kindid;
    }

    public Member getMID() {
        return MID;
    }

    public void setMID(Member MID) {
        this.MID = MID;
    }
}
