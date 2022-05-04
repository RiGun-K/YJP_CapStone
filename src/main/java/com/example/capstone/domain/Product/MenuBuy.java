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
    private String savedTime;
    @LastModifiedDate
    private String modifiedDate;

    // 구매종류
    @ManyToOne()
    @JoinColumn(name = "kindid")
    private Kind kindid;

    // 판매자 정보
    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;


    // fetch = FetchType.LAZY 쓰면 외래키 조회 불가 !
//    @JsonBackReference
    // cascade = CascadeType.ALL ( 삭제되면 같이삭제 )


    public MenuBuy() {}
    public MenuBuy(String buyName, int buyPrice, String buyEx, String savedTime, int buyStock, String origFilename, String filename, String filePath, Kind kindid, Member MID) {
        this.buyName = buyName;
        this.buyPrice = buyPrice;
        this.buyEx = buyEx;
        this.savedTime = savedTime;
        this.buyStock = buyStock;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.kindid = kindid;
        this.MID = MID;
    }

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
