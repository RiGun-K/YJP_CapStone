package com.example.capstone.domain.order;


import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.CampingDetail;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;

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
    @JoinColumn(name = "detailId")
    private CampingDetail campingDetail;

    @ManyToOne
    @JoinColumn(name = "campingId")
    private Camping camping;

    @Column
    private int orderMenuCount;

    public int getOrderMenuId() {
        return orderMenuId;
    }

    public void setOrderMenuId(int orderMenuId) {
        this.orderMenuId = orderMenuId;
    }

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

    public int getOrderMenuCount() {
        return orderMenuCount;
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

    public Camping getCamping() {
        return camping;
    }

    public void setCamping(Camping camping) {
        this.camping = camping;
    }
}