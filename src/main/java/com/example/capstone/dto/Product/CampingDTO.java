package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CampingDTO {
    private int campingId;
    private String campingName;
    private String campingInfo;
    private String campingState;
    private String campingDetailState;


    private String address;
    private String detailAddress;

    private String origFilename;
    private String filename;
    private String filePath;


    private LocalDate savedTime;

    private int infoterId;
    private String mid;
}
