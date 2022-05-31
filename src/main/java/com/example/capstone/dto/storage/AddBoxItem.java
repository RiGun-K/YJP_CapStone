package com.example.capstone.dto.storage;


import java.util.List;

public class AddBoxItem {
    private long useBoxCode;
    private List<Long> itemList;
    public AddBoxItem() {
    }

    public AddBoxItem(long useBoxCode, List<Long> itemList) {
        this.useBoxCode = useBoxCode;
        this.itemList = itemList;
    }

    public long getUseBoxCode() {
        return useBoxCode;
    }

    public void setUseBoxCode(long useBoxCode) {
        this.useBoxCode = useBoxCode;
    }

    public List<Long> getItemList() {
        return itemList;
    }

    public void setItemList(List<Long> itemList) {
        this.itemList = itemList;
    }
}
