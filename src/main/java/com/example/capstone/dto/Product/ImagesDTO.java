package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
public class ImagesDTO {

    private Long imageId;
    private String origFilename;
    private String filename;
    private String filePath;

    private int buyId;
    private int rentalId;
    private int campingId;
    private int detailId;
}
