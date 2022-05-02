package com.example.capstone.domain.order;


import com.example.capstone.domain.Product.Menu;

import javax.persistence.*;

@Entity
@Table(name="orderMenu")
@IdClass(OrderMenuPK.class)
public class OrderMenu {
    @Id
    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orders;

    @Id
    @ManyToOne
    @JoinColumn(name = "menuid")
    private Menu menu;

    @Column
    private int orderMenuCount;

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
