package com.example.capstone.dto.storage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// DTO 장비 장소 이동 결제
@Getter
@Setter
public class RoundMove {
    private String userId;
    private long useBoxCode;
    private String name;
    private String zipCode;
    private String address;
    private String detailAddress;
    private int price;
    private String tel;
    private List<ItemDTO> list;

    public RoundMove() {}
}
