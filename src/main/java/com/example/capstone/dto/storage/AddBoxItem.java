package com.example.capstone.dto.storage;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddBoxItem {
    private long useBoxCode;
    private List<Long> itemList;
    public AddBoxItem() {
    }

}
