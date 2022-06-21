package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import com.example.capstone.domain.Product.CampingArea;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name="board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    @Column()
    private String title;
    private String content;

    @Column()
    private String origFilename;
    @Column
    private String filename;
    @Column()
    private String filePath;

    @Column
    private Integer boardViews = 0;
    @CreatedDate
    private String savedTime;

    @ManyToOne()
    @JoinColumn(name = "MID")
    private Member MID;

    // 부모
    @ManyToOne
    @JoinColumn(name = "parentBoardId")
    @JsonManagedReference
    private Board parentBoard;

    // 자식
    @OneToMany(mappedBy = "parentBoard", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonBackReference
    private List<Board> childrenBoard = new ArrayList<>();

    public Board(){}

    public Board(Long boardId, String title, String content, String origFilename, String filename, String filePath, Integer boardViews, String savedTime, Member MID) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.boardViews = boardViews;
        this.savedTime = savedTime;
        this.MID = MID;
    }

    public Board(Long boardId, String title, String content, String origFilename, String filePath, String filename, String savedTime, Member MID) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.savedTime = savedTime;
        this.MID = MID;
    }

    public Board(String title, String content, String origFilename, String filePath, String filename,  String savedTime, Member MID) {
        this.title = title;
        this.content = content;
        this.origFilename = origFilename;
        this.filePath = filePath;
        this.filename = filename;
        this.savedTime = savedTime;
        this.MID = MID;
    }

    public Board(String content, String savedTime, Member MID, Board parentBoard) {
        this.content = content;
        this.savedTime = savedTime;
        this.MID = MID;
        this.parentBoard = parentBoard;
    }
}
