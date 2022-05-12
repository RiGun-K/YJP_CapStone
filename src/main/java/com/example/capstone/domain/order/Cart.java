package com.example.capstone.domain.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Menu;

import javax.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartCode;

    @Column
    private int cartItemCount;
    private int cartItemAllPrice;

    @ManyToOne
    @JoinColumn(name = "menuid")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "MCode")
    private Member MCode;

    public int getCartCode() {
        return cartCode;
    }

    public void setCartCode(int cartCode) {
        this.cartCode = cartCode;
    }

    public int getCartItemCount() {
        return cartItemCount;
    }

    public void setCartItemCount(int cartItemCount) {
        this.cartItemCount = cartItemCount;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Member getMCode() {
        return MCode;
    }

    public void setMCode(Member MCode) {
        this.MCode = MCode;
    }

    public int getCartItemAllPrice() {
        return cartItemAllPrice;
    }

    public void setCartItemAllPrice(int cartItemAllPrice) {
        this.cartItemAllPrice = cartItemAllPrice;
    }
}