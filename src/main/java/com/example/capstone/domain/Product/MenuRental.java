package com.example.capstone.domain.Product;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name="menurental")
public class MenuRental {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int rentalId;

    @Column()
    private String rentalName;
    @Column()
    private String rentalEx;
    @Column()
    private int rentalStock;

    // 체크리스트를 통해서 n일,주,개월 당 m원 으로 생성해주기
    @Column()
    private int rentalPrice;
    // 시작기간
    @Column
    private LocalDate startDate;
    // 반환기간
    @Column
    private LocalDate endDate;


    // 렌탈상태 ( 0 = 마감, 1 = 운영, 2 = 기타 )
    @Column(length = 1)
    private String rentalState;

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

    // 렌탈 종류
    @ManyToOne()
    @JoinColumn(name = "kindid")
    private Kind kindid;

    // 판매자 정보
    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;

    public MenuRental() {}
    public MenuRental(String rentalName, int rentalPrice, String rentalEx, String savedTime, int rentalStock, String origFilename, String filename, String filePath, Kind kindid, Member MID) {
        this.rentalName = rentalName;
        this.rentalPrice = rentalPrice;
        this.rentalEx = rentalEx;
        this.savedTime = savedTime;
        this.rentalStock = rentalStock;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.kindid = kindid;
        this.MID = MID;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public String getRentalName() {
        return rentalName;
    }

    public void setRentalName(String rentalName) {
        this.rentalName = rentalName;
    }

    public String getRentalEx() {
        return rentalEx;
    }

    public void setRentalEx(String rentalEx) {
        this.rentalEx = rentalEx;
    }

    public int getRentalStock() {
        return rentalStock;
    }

    public void setRentalStock(int rentalStock) {
        this.rentalStock = rentalStock;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRentalState() {
        return rentalState;
    }

    public void setRentalState(String rentalState) {
        this.rentalState = rentalState;
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
