package com.example.capstone.dto.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {

    private String mid;
    private String title;
    private String content;
    private String origFilename;
    private String filePath;
    private Integer recommend = 0;
    private String savedTime;



}
