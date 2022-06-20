package com.example.capstone.dto.storage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageData {
//    private Box box;
//    private Storage storage;
//    private MultipartFile file;
//
//    public StorageData() {
//    }
//
//    public StorageData(Box box, Storage storage, MultipartFile file) {
//        this.box = box;
//        this.storage = storage;
//        this.file = file;
//    }
//
//    public Box getBox() {
//        return box;
//    }
//
//    public void setBox(Box box) {
//        this.box = box;
//    }
//
//    public Storage getStorage() {
//        return storage;
//    }
//
//    public void setStorage(Storage storage) {
//        this.storage = storage;
//    }
//
//    public MultipartFile getFile() {
//        return file;
//    }
//
//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }

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
    private String origFilename;
    private String filename;
    private String filePath;

}
