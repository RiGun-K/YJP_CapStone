package com.example.capstone.dto.Board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;

@Getter
@Setter
public class CampingBoardDTO {

    private String mcode;
    private Long boardMenuCode;

    private String campingTitle;
    private String campingContent;
    private String origFilename;
    private String filePath;
    private Integer recommend = 0;
    private String savedTime;

}
