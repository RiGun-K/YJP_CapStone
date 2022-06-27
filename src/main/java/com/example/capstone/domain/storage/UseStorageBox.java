package com.example.capstone.domain.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.order.Orders;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USESTORAGEBOX")
@Getter
@Setter
public class UseStorageBox {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long useStorageBoxCode;

    @Column
    private LocalDate useStorageStartTime;

    @Column
    private LocalDate useStorageEndTime;

    @Column
    private String useStorageState = "0";

    @ManyToOne
    @JoinColumn(name = "storageBoxCode")
    private StorageBox storageBoxCode;

    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orderCode;

    @ManyToOne
    @JoinColumn(name = "MCode")
    private Member MCode;

    @JsonManagedReference
    @OneToMany(mappedBy = "useStorageBoxCode",cascade = CascadeType.ALL)
    private List<BoxItem> boxItemList = new ArrayList<>();

    public UseStorageBox() {
    }

    public UseStorageBox(LocalDate useStorageStartTime, LocalDate useStorageEndTime, StorageBox storageBoxCode, Orders orderCode, Member MCode) {
        this.useStorageStartTime = useStorageStartTime;
        this.useStorageEndTime = useStorageEndTime;
        this.storageBoxCode = storageBoxCode;
        this.orderCode = orderCode;
        this.MCode = MCode;
    }


    public UseStorageBox(LocalDate useStorageStartTime, LocalDate useStorageEndTime, StorageBox storageBoxCode, Orders orderCode) {
        this.useStorageStartTime = useStorageStartTime;
        this.useStorageEndTime = useStorageEndTime;
        this.storageBoxCode = storageBoxCode;
        this.orderCode = orderCode;
    }

    public UseStorageBox(LocalDate useStorageStartTime, LocalDate useStorageEndTime, String useStorageState, StorageBox storageBoxCode, Orders orderCode) {
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

}
