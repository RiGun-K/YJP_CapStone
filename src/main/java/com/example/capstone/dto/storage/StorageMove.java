package com.example.capstone.dto.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageMove {

    private String userId;
    private long use;
    private long before;
    private long after;
    private int price;

}
