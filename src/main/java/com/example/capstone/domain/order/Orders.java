package com.example.capstone.domain.order;

import com.example.capstone.domain.Member.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int orderCode;

    @Column
    private int orderPrice; //주문금액
    @Column
    private char orderType; //주문유형
    @Column
    private String paymentCode; //결제코드
    @Column
    private LocalDateTime paymentDate;  //결제날짜
    @Column
    private String paymentInfo; //결제정보
    @Column
    private String deliveryGetter; //받는 사람
    @Column
    private String deliveryGetterTel; //받는 사람 연락처
    @Column
    private String deliveryZipcode; //배송지 우편번호
    @Column
    private String deliveryAddress; //배송지 상세주소
    @Column
    private char orderState; //주문상태
    @Column
    private String deliveryRequest; //배송요청사항
    @Column
    private LocalDate StartDate; //대여&예약 시작일
    @Column
    private LocalDate EndDate; //대여&예약 종료일
    @Column
    private String deliveryHopeTime;

    @ManyToOne
    @JoinColumn(name = "MID")
    private Member MID;

    public Orders() {

    }

    public Orders(Member MCode) {
        this.MCode = MCode;
    }

    public Orders(Member MCode, int orderPrice) {
        this.orderPrice = orderPrice;
        this.MCode = MCode;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public char getOrderType() {
        return orderType;
    }

    public void setOrderType(char orderType) {
        this.orderType = orderType;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDeliveryGetter() {
        return deliveryGetter;
    }

    public void setDeliveryGetter(String deliveryGetter) {
        this.deliveryGetter = deliveryGetter;
    }

    public String getDeliveryRequest() {
        return deliveryRequest;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getDeliveryZipcode() {
        return deliveryZipcode;
    }

    public void setDeliveryZipcode(String deliveryZipcode) {
        this.deliveryZipcode = deliveryZipcode;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public char getOrderState() {
        return orderState;
    }

    public void setOrderState(char orderState) {
        this.orderState = orderState;
    }

    public String getDelivaryRequest() {
        return deliveryRequest;
    }

    public void setDeliveryRequest(String deliveryRequest) {
        this.deliveryRequest = deliveryRequest;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public String getDeliveryHopeTime() {
        return deliveryHopeTime;
    }

    public void setDeliveryHopeTime(String deliveryHopeTime) {
        this.deliveryHopeTime = deliveryHopeTime;
    }

    public Member getMID() {
        return MID;
    }

    public void setMID(Member MID) {
        this.MID = MID;
    }

    public Orders(int orderCode, int orderPrice, char orderType, String paymentCode, LocalDateTime paymentDate, String paymentInfo, String deliveryZipcode, String deliveryAddress, char orderState, String deliveryRequest, LocalDate startDate, LocalDate endDate, String deliveryHopeTime, Member MID) {
        this.orderCode = orderCode;
        this.orderPrice = orderPrice;
        this.orderType = orderType;
        this.paymentCode = paymentCode;
        this.paymentDate = paymentDate;
        this.paymentInfo = paymentInfo;
        this.deliveryZipcode = deliveryZipcode;
        this.deliveryAddress = deliveryAddress;
        this.orderState = orderState;
        this.deliveryRequest = deliveryRequest;
        StartDate = startDate;
        EndDate = endDate;
        this.deliveryHopeTime = deliveryHopeTime;
        this.MID = MID;
    }

    public Orders( Member MID, int orderPrice) {
        this.orderPrice = orderPrice;
        this.MID = MID;
    }

    public Orders(Member MID) {
        this.MID = MID;
    }
}
