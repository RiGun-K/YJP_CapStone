package com.example.capstone.domain.order;


import com.example.capstone.domain.Product.Menu;

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
    @JoinColumn(name = "menuid")
    private Menu menu;

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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getOrderMenuCount() {
        return orderMenuCount;
    }

    public void setOrderMenuCount(int orderMenuCount) {
        this.orderMenuCount = orderMenuCount;
    }
}
