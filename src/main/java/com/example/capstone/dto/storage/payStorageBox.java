package com.example.capstone.dto.storage;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class payStorageBox {

    private String userId;
    private long storageBoxCode;
    private LocalDate useStorageStartTime;
    private LocalDate useStorageEndTime;
    private int price;
    private List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();

    public payStorageBox() {
    }


}
