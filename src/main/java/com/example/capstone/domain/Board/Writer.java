package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ToString
@Entity
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

    //부모
    @ManyToOne
    @JoinColumn(name = "parentWcode")
    @JsonManagedReference
    private Writer parentWriter;

    //자식
    @OneToMany(mappedBy = "parentWriter")
    @JsonBackReference
    private List<Writer> childrenWriter = new ArrayList<>();

    public Writer(String title, String content, Member MID) {
        this.title = title;
        this.content = content;
        this.MID = MID;
    }

    public Writer() {

    }


    public Writer(String content, Member MID, Writer parentWriter) {
        this.content = content;
        this.MID = MID;
        this.parentWriter = parentWriter;
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

    public Writer getParentWriter() {
        return parentWriter;
    }

    public void setParentWriter(Writer parentWriter) {
        this.parentWriter = parentWriter;
    }

    public List<Writer> getChildrenWriter() {
        return childrenWriter;
    }

    public void setChildrenWriter(List<Writer> childrenWriter) {
        this.childrenWriter = childrenWriter;
    }
}


