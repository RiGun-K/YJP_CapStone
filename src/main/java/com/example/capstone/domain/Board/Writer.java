package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Data;

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

    @ManyToOne()
    @JoinColumn(name = "MID")
    private Member MID;

    public Writer(String title, String content, Member MID) {
        this.title = title;
        this.content = content;
        this.MID = MID;
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
}


