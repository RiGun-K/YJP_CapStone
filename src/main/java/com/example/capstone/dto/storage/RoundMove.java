package com.example.capstone.dto.storage;
// DTO 장비 장소 이동 결제
public class RoundMove {
    private String userId;
    private long useBoxCode;
    private String zipCode;
    private String address;
    private String detailAddress;
    private int price;

    public RoundMove() {
    }

    public RoundMove(String userId, long useBoxCode, String zipCode, String address, String detailAddress) {
        this.userId = userId;
        this.useBoxCode = useBoxCode;
        this.zipCode = zipCode;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getUseBoxCode() {
        return useBoxCode;
    }

    public void setUseBoxCode(long useBoxCode) {
        this.useBoxCode = useBoxCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
