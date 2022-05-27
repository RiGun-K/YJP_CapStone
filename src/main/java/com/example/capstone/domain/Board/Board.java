package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;


@Entity
@Data
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
    @JoinColumn(name = "MCODE")
    private Member mcode;

    public Board(){}

    public Board(Long boardId, String title, String content, String origFilename, String filename, String filePath, Integer boardViews, String savedTime, Member mcode) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
        this.boardViews = boardViews;
        this.savedTime = savedTime;
        this.mcode = mcode;
    }

}
