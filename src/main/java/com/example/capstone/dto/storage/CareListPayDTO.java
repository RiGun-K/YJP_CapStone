package com.example.capstone.dto.storage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CareListPayDTO {
    private List<RepairDTO> list;
    private String mid;
    private String text;
    private int price;

}