package com.example.capstone.domain.order;

import com.example.capstone.domain.Member.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderCode;

    @Column
    private int orderPrice; //주문금액
    @Column
    private String orderType; //주문유형
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
    private String orderState; //주문상태
    @Column
    private String deliveryRequest; //배송요청사항
    @Column
    private LocalDate StartDate; //대여&예약 시작일
    @Column
    private LocalDate EndDate; //대여&예약 종료일
    @Column
    private String deliveryHopeTime; //배송희망시간

    @ManyToOne
    @JoinColumn(name = "MCode")
    private Member MCode;

    public Orders() {

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
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

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getDeliveryGetter() {
        return deliveryGetter;
    }

    public void setDeliveryGetter(String deliveryGetter) {
        this.deliveryGetter = deliveryGetter;
    }

    public String getDeliveryGetterTel() {
        return deliveryGetterTel;
    }

    public void setDeliveryGetterTel(String deliveryGetterTel) {
        this.deliveryGetterTel = deliveryGetterTel;
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getDeliveryRequest() {
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

    public Member getMCode() {
        return MCode;
    }

    public void setMCode(Member MCode) {
        this.MCode = MCode;
    }
}
