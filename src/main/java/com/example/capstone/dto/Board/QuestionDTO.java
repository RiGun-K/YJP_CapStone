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
    private String q_savedTime;

    private String mid;
}
