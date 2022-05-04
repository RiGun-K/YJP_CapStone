package com.example.capstone.domain.storage;

import com.example.capstone.domain.order.Orders;

import javax.persistence.*;

@Entity
@Table(name = "STORAGEQUIPMENT")
public class StorageEquipment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long storageEquipment;

    @ManyToOne
    @JoinColumn(name = "memEquipmentCode")
    private MemberEquipment memEquipmentCode;

    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orderCode;

    public StorageEquipment() {
    }

    public long getStorageEquipment() {
        return storageEquipment;
    }

    public void setStorageEquipment(long storageEquipment) {
        this.storageEquipment = storageEquipment;
    }

    public MemberEquipment getMemEquipmentCode() {
        return memEquipmentCode;
    }

    public void setMemEquipmentCode(MemberEquipment memEquipmentCode) {
        this.memEquipmentCode = memEquipmentCode;
    }

    public Orders getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Orders orderCode) {
        this.orderCode = orderCode;
    }
}
