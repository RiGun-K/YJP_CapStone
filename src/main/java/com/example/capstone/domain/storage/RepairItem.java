package com.example.capstone.domain.storage;

import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.Orders;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "REPAIRITEM")
@Getter
@Setter
public class RepairItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long repairItemCode;

    @Column
    private int repairItemCount;

    @Column
    private String repairItemState;

    @ManyToOne
    @JoinColumn(name = "boxItemCode")
    private BoxItem boxItemCode;

    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orderCode;

    @ManyToOne
    @JoinColumn(name = "buyId")
    private MenuBuy buyId;

    public RepairItem() {
    }
}
