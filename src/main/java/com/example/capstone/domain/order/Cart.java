package com.example.capstone.domain.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartCode;

    @Column
    private int cartItemCount;

    @ManyToOne
    @JoinColumn(name = "MCode")
    private Member MCode;

    @ManyToOne
    @JoinColumn(name = "buyId")
    private MenuBuy menuBuy;


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


    public Member getMCode() {
        return MCode;
    }

    public void setMCode(Member MCode) {
        this.MCode = MCode;
    }

}