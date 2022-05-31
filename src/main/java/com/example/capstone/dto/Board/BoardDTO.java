package com.example.capstone.dto.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

    private String title;
    private String content;
    private String origFilename;
    private String filename;
    private String filePath;
    private Integer boardViews = 0;
    private String savedTime;
    private Long parentBoard;

    private String mid;

}
