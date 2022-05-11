package com.example.capstone.dto.Product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
public class CampingDetailDTO {
    private int detailId;

    private String detailName;
    private String detailFunction;
    private String detailEx;
    private int detailPrice;

    private Integer baseNumber;
    private Integer maximumNumber;

    private String origFilename;
    private String filename;
    private String filePath;

    private String savedTime;
    private int campingId;
}
