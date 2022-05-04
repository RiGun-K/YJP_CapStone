package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MenuRentalDTO {

    // 실제 DB에 저장할 값들 ( 일치해야함 , NOT NULL )
    private int rentalId;
    private String rentalName;
    private int rentalPrice;
    private String rentalEx;
    private String savedTime;
    private int rentalStock;
    private String origFilename;
    private String filename;
    private String filePath;

    private int kindid;
    private String mid;
}
