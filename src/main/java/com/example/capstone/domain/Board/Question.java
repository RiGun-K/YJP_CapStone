package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;

    @Column()
    private String q_title;
    private String q_content;

    @Column()
    private String origFilename;
    @Column()
    private String filename;
    @Column()
    private String filePath;

    @Column
    private Integer boardViews = 0;
    @CreatedDate
    private String q_savedTime;

    @ManyToOne()
    @JoinColumn(name = "MID")
    private Member MID;

    public Question(){

    }

    public Question(String q_title, String q_content, String origFilename, String filePath, String filename, Integer boardViews, String q_savedTime, Member MID) {
        this.q_title = q_title;
        this.q_content = q_content;
        this.origFilename = origFilename;
        this.filePath = filePath;
        this.filename = filename;
        this.boardViews = boardViews;
        this.q_savedTime = q_savedTime;
        this.MID = MID;
    }

}
