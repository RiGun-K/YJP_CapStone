package com.example.capstone.controller.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.Cart;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.repository.orders.CartRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class CartController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MenuBuyRepository menuBuyRepository;

    @PostMapping("/buyCartPut")
    public Boolean postBuyCart(@RequestBody HashMap<String, String> buyCart){
        Cart cart = new Cart();

        Optional<Member> searchMember = memberRepository.findByMID(buyCart.get("MID"));

        if(searchMember.isPresent()){
            Member member = searchMember.get();

            cart.setMCode(member);
            cart.setCartItemCount(Integer.parseInt(buyCart.get("count")));

            Optional<MenuBuy> searchMenuBuy = menuBuyRepository.findById(Integer.parseInt(buyCart.get("buyId")));
            if(searchMenuBuy.isPresent()) {
                MenuBuy menuBuy = searchMenuBuy.get();

                List<Cart> cartList = cartRepository.findAll();

                for(int i=0; i < cartList.size(); i++){
                    MenuBuy searchMenuBuyList = cartList.get(i).getMenuBuy();

                    if(searchMenuBuyList == menuBuy){
                        System.out.println("이미 카트에 있음");
                        return true;
                    }
                }

                cart.setMenuBuy(menuBuy);

                cartRepository.save(cart);
            }
        }
        return false;
    }
}
