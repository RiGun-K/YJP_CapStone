package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CampingDTO {

    private int campingId;
    private String campingName;
    private String campingInfo;
    private String campingDetailState;

    private String postalAddress;
    private String address;
    private String detailAddress;


    private String savedTime;

    private String origFilename;
    private String filename;
    private String filePath;

    private int campingViews = 0;

    private int infoterId;
    private int areaId;
    private String mid;
}
