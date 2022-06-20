package com.example.capstone.dto.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageData {

    private int small;
    private int medium;
    private int large;
    private String storageName;
    private String storageZipcode;
    private String storageAddress;
    private String storageDetailAddress;
    private String storageTel;
    private double latitude;//위도
    private double longitude;//경도
    private String filename;
    private String filePath;

}
