package com.example.capstone.domain.storage;

import com.example.capstone.domain.order.Orders;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USESTORAGEBOX")
public class UseStorageBox {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long useStorageBoxCode;

    @Column
    private LocalDateTime useStorageStartTime;

    @Column
    private LocalDateTime useStorageEndTime;

    @Column
    private String useStorageState = "0";

    @ManyToOne
    @JoinColumn(name = "storageBoxCode")
    private StorageBox storageBoxCode;

    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orderCode;

    public UseStorageBox() {
    }

    public UseStorageBox(LocalDateTime useStorageStartTime, LocalDateTime useStorageEndTime, StorageBox storageBoxCode, Orders orderCode) {
        this.useStorageStartTime = useStorageStartTime;
        this.useStorageEndTime = useStorageEndTime;
        this.storageBoxCode = storageBoxCode;
        this.orderCode = orderCode;
    }

    public UseStorageBox(LocalDateTime useStorageStartTime, LocalDateTime useStorageEndTime, String useStorageState, StorageBox storageBoxCode, Orders orderCode) {
        this.useStorageStartTime = useStorageStartTime;
        this.useStorageEndTime = useStorageEndTime;
        this.useStorageState = useStorageState;
        this.storageBoxCode = storageBoxCode;
        this.orderCode = orderCode;
    }

    public UseStorageBox(StorageBox storageBoxCode, Orders orderCode) {
        this.storageBoxCode = storageBoxCode;
        this.orderCode = orderCode;
    }

    public UseStorageBox(long useStorageBoxCode, StorageBox storageBoxCode, Orders orderCode) {
        this.useStorageBoxCode = useStorageBoxCode;
        this.storageBoxCode = storageBoxCode;
        this.orderCode = orderCode;
    }

    public long getUseStorageBoxCode() {
        return useStorageBoxCode;
    }

    public void setUseStorageBoxCode(long useStorageBoxCode) {
        this.useStorageBoxCode = useStorageBoxCode;
    }

    public LocalDateTime getUseStorageStartTime() {
        return useStorageStartTime;
    }

    public void setUseStorageStartTime(LocalDateTime useStorageStartTime) {
        this.useStorageStartTime = useStorageStartTime;
    }

    public LocalDateTime getUseStorageEndTime() {
        return useStorageEndTime;
    }

    public void setUseStorageEndTime(LocalDateTime useStorageEndTime) {
        this.useStorageEndTime = useStorageEndTime;
    }

    public String getUseStorageState() {
        return useStorageState;
    }

    public void setUseStorageState(String useStorageState) {
        this.useStorageState = useStorageState;
    }

    public StorageBox getStorageBoxCode() {
        return storageBoxCode;
    }

    public void setStorageBoxCode(StorageBox storageBoxCode) {
        this.storageBoxCode = storageBoxCode;
    }

    public Orders getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Orders orderCode) {
        this.orderCode = orderCode;
    }
}
