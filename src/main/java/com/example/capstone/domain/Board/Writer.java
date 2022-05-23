package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Data
@Table(name="writer")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int writer_code;

    @Column()
    private String title;
    private String content;

    @Column()
    private String origFilename;
    @Column()
    private String filePath;

    @Column
    private Integer recommend = 0;
    @CreatedDate
    private String savedTime;

    @ManyToOne()
    @JoinColumn(name = "MID")
    private Member MID;

    public Writer(String title, String content, Member MID, String origFilename, String filePath, Integer recommend, String savedTime) {
        this.title = title;
        this.content = content;
        this.MID = MID;
        this.origFilename = origFilename;
        this.filePath = filePath;
        this.recommend = recommend;
        this.savedTime = savedTime;
    }

    public Writer() {

    }

    public int getWriter_code() {
        return writer_code;
    }

    public void setWriter_code(int writer_code) {
        this.writer_code = writer_code;
    }

    public Member getMID() {
        return MID;
    }

    public void setMID(Member MID) {
        this.MID = MID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrigFilename() {
        return origFilename;
    }

    public void setOrigFilename(String origFilename) {
        this.origFilename = origFilename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(String savedTime) {
        this.savedTime = savedTime;
    }
}


