package com.example.capstone.domain.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;

import javax.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartCode;

    @Column
    private int cartItemCount;

    @ManyToOne
    @JoinColumn(name = "buyId")
    private MenuBuy menuBuy;

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

    public MenuBuy getMenuBuy() {
        return menuBuy;
    }

    public void setMenuBuy(MenuBuy menuBuy) {
        this.menuBuy = menuBuy;
    }

    public Member getMCode() {
        return MCode;
    }

    public void setMCode(Member MCode) {
        this.MCode = MCode;
    }

}