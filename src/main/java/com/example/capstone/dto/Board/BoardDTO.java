package com.example.capstone.dto.Board;

import com.example.capstone.domain.Board.Writer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

    private int writer_code;
    private String mid;
    private int parentWriter;
    private String title;
    private String content;
}
