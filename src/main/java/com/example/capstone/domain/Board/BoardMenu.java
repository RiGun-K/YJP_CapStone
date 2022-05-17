package com.example.capstone.domain.Board;

import lombok.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
public class BoardMenu {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boardMenuCode;

    @Column
    private String boardMenuName;

    public BoardMenu() {}

    public BoardMenu(Long boardMenuCode, String boardMenuName) {
        this.boardMenuCode = boardMenuCode;
        this.boardMenuName = boardMenuName;
    }
}
