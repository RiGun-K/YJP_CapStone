package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MenuDTO {

    private int menuid;
    private String menuname;
    private int price;
    private String ex;
    private LocalDate savedTime;
    private int stock;
    private String origFilename;
    private String filename;
    private String filePath;

    private int kindid;
    private String mid;

}