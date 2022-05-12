package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MenuBuyDTO {

    private int buyid;
    private String buyName;
    private int buyPrice;
    private String buyEx;
    private String savedTime;
    private int buyStock;
    private String origFilename;
    private String filename;
    private String filePath;

    private int kindid;
    private String mid;
}
