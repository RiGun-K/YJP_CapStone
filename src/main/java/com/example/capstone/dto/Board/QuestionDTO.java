package com.example.capstone.dto.Board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionDTO {

    private Long questionId;
    private String q_title;
    private String q_content;
    private String origFilename;
    private String filename;
    private String filePath;
    private Integer boardViews = 0;
    private String q_savedTime;

    private String mid;
}
