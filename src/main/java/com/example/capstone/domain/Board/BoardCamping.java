package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
public class BoardCamping {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boardCampingCode;

    @Column
    private String campingTitle;
    @Column
    private String campingContent;

    @Column()
    private String origFilename;
    @Column
    private String filename;
    @Column()
    private String filePath;

    @Column
    private Integer recommend = 0;
    @CreatedDate
    private String savedTime;

    @ManyToOne()
    @JoinColumn(name = "MCODE")
    private Member mcode;

    @ManyToOne()
    @JoinColumn(name = "boardMenuCode")
    private BoardMenu boardMenuCode;

    @ManyToOne()
    @JoinColumn(name = "campingid")
    private Camping campingId;

    public BoardCamping() {}

    public BoardCamping(String campingTitle, String campingContent, Member mcode, BoardMenu boardMenuCode, Camping camping, String savedTime, String origFilename, String filename, String filePath) {
        this.campingTitle = campingTitle;
        this.campingContent = campingContent;
        this.mcode = mcode;
        this.boardMenuCode = boardMenuCode;
        this.campingId = camping;
        this.savedTime = savedTime;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

    public BoardCamping(Long boardCampingCode, String campingTitle, String campingContent, Member mcode, BoardMenu boardMenuCode, Camping camping, String savedTime, String origFilename, String filename, String filePath, Integer recommend) {
        this.boardCampingCode = boardCampingCode;
        this.campingTitle = campingTitle;
        this.campingContent = campingContent;
        this.mcode = mcode;
        this.boardMenuCode = boardMenuCode;
        this.campingId = camping;
        this.savedTime = savedTime;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.recommend = recommend;

    }
}
