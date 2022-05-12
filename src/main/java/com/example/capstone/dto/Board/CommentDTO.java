package com.example.capstone.dto.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {

    private String commenttext;
    private LocalDateTime commentdate;
    private String MID;
}
