package com.example.capstone.domain.order;


import com.example.capstone.domain.Product.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="orderMenu")
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderMenuId;

    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orders;


    @ManyToOne
    @JoinColumn(name = "menuBuyId")
    private MenuBuy menuBuy;

    @ManyToOne
    @JoinColumn(name = "rentalId")
    private MenuRental rental;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "campingId")
    private Camping camping;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "detailId")
    private CampingDetail campingDetail;

    @Column
    private int orderMenuCount;

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public MenuBuy getMenuBuy() {
        return menuBuy;
    }

    public void setMenuBuy(MenuBuy menuBuy) {
        this.menuBuy = menuBuy;
    }

    public MenuRental getRental() {
        return rental;
    }

    public void setRental(MenuRental rental) {
        this.rental = rental;
    }

    public int getOrderMenuCount() {
        return orderMenuCount;
    }

    public int getOrderMenuId() {
        return orderMenuId;
    }

    public void setOrderMenuId(int orderMenuId) {
        this.orderMenuId = orderMenuId;
    }

    public Camping getCamping() {
        return camping;
    }

    public void setCamping(Camping camping) {
        this.camping = camping;
    }

    public void setOrderMenuCount(int orderMenuCount) {
        this.orderMenuCount = orderMenuCount;
    }

    public CampingDetail getCampingDetail() {
        return campingDetail;
    }

    public void setCampingDetail(CampingDetail campingDetail) {
        this.campingDetail = campingDetail;
    }
}