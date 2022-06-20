package com.example.capstone.controller.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import com.example.capstone.domain.order.Cart;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.repository.Product.MenuRentalRepository;
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

    @Autowired
    private MenuRentalRepository menuRentalRepository;

    @Autowired
    private CampingRepository campingRepository;

    @PostMapping("/buyCartPut")
    public boolean postBuyCart(@RequestBody HashMap<String, String> buyCart){
        Cart cart = new Cart();

        Optional<Member> searchMember = memberRepository.findByMCode(Long.parseLong(buyCart.get("mid")));

        if(searchMember.isPresent()){
            Member member = searchMember.get();

            System.out.println("123");

            cart.setMCode(member);
            cart.setCartItemCount(Integer.parseInt(buyCart.get("count")));

            Optional<MenuBuy> searchMenuBuy = menuBuyRepository.findById(Integer.parseInt(buyCart.get("buyId")));
            if(searchMenuBuy.isPresent()) {
                MenuBuy menuBuy = searchMenuBuy.get();

                List<Cart> cartList = cartRepository.findAll();

                for(int i=0; i < cartList.size(); i++){
                    Member searchMember1 = cartList.get(i).getMCode();

                    if(searchMember1 == member){
                        MenuBuy searchMenuBuyList = cartList.get(i).getMenuBuy();

                        if(searchMenuBuyList == menuBuy){
                            return false;
                        }
                    }
                }

                cart.setMenuBuy(menuBuy);

                cartRepository.save(cart);
            }
        }
        return true;
    }

    @GetMapping("/cartList/buyCart/{MCode}")
    public List<Cart> getBuyCartList(@PathVariable("MCode") Long MCode){
        Optional<Member> searchMember = memberRepository.findById(MCode);
        if(searchMember.isPresent()){
            Member member = searchMember.get();
            List<Cart> buyCartMemberList = cartRepository.findByMenuBuy(member);

            return buyCartMemberList;
        }
        return null;
    }

    @PostMapping("/cartItem/count")
    public int postCartItemSub(@RequestBody HashMap<String, String> cartItemData){
        Optional<Member> searchMember = memberRepository.findByMCode((Long.parseLong(cartItemData.get("mid"))));

        if(searchMember.isPresent()){
            Member member = searchMember.get();

            List<Cart> cart = cartRepository.findByMenuBuy(member);



            for(int i=0; i<cart.size(); i++){
                MenuBuy searchMenuBuy = cart.get(i).getMenuBuy();

                if(Integer.parseInt(cartItemData.get("cartMenuBuy")) == searchMenuBuy.getBuyId()){
                    cart.get(i).setCartItemCount(Integer.parseInt(cartItemData.get("cartItemCount")));

                    cartRepository.save(cart.get(i));

                    return cart.get(i).getCartItemCount();
                }else{
                    System.out.println("no");
                }
            }

        }
        return 1;
    }

    @GetMapping("/buyCartOrder/{cartCode}")
    public Cart getBuyCartOrder(@PathVariable("cartCode") int cartCode){
        Optional<Cart> searchCart = cartRepository.findById(cartCode);

        System.out.println("1");

        if(searchCart.isPresent()){
            Cart cart = searchCart.get();

            System.out.println("2");

            return cart;
        }
        return null;
    }

    @PostMapping("/buyCartDelete")
    public String postBuyCartDelete(@RequestBody HashMap<String, String> cartItemData){
        Optional<Cart> searchCart = cartRepository.findById(Integer.parseInt(cartItemData.get("cartCode")));

        if(searchCart.isPresent()){
            cartRepository.delete(searchCart.get());

            return "sc";
        }

        return "er";
    }

    @PostMapping("/shareCartPut")
    public boolean postShareCart(@RequestBody HashMap<String, String> shareCart){
        Cart cart = new Cart();

        Optional<Member> searchMember = memberRepository.findByMCode(Long.parseLong(shareCart.get("mid")));



        if(searchMember.isPresent()){
            Member member = searchMember.get();

            cart.setMCode(member);

            Optional<MenuRental> searchRental = menuRentalRepository.findById(Integer.parseInt(shareCart.get("rentalId")));
            if(searchRental.isPresent()) {
                MenuRental rental = searchRental.get();

                List<Cart> cartList = cartRepository.findAll();

                for(int i=0; i < cartList.size(); i++){
                    Member searchMember1 = cartList.get(i).getMCode();

                    if(searchMember1 == member){
                        MenuRental searchListInRental = cartList.get(i).getRental();

                        if(searchListInRental == rental){
                            return false;
                        }
                    }
                }

                cart.setRental(rental);

                cartRepository.save(cart);

            }
        }
        return true;
    }

    @GetMapping("/cartList/shareCart/{MCode}")
    public List<Cart> getShareCartList(@PathVariable("MCode") Long MCode){
        Optional<Member> searchMember = memberRepository.findById(MCode);
        if(searchMember.isPresent()){
            Member member = searchMember.get();
            List<Cart> shareCartMemberList = cartRepository.findByMenuRental(member);

            System.out.println(shareCartMemberList.size());

            return shareCartMemberList;
        }
        return null;
    }

    @PostMapping("/shareCartDelete")
    public String postShareCartDelete(@RequestBody HashMap<String, String> cartItemData){
        Optional<Cart> searchCart = cartRepository.findById(Integer.parseInt(cartItemData.get("cartCode")));

        if(searchCart.isPresent()){
            cartRepository.delete(searchCart.get());

            return "sc";
        }

        return "er";
    }

    @GetMapping("/cartList/reservationCart/{MCode}")
    public List<Cart> getReservationCartList(@PathVariable("MCode") Long MCode){
        Optional<Member> searchMember = memberRepository.findById(MCode);
        if(searchMember.isPresent()){
            Member member = searchMember.get();
            List<Cart> resCartMemberList = cartRepository.findByCamping(member);

            return resCartMemberList;
        }
        return null;
    }

    @PostMapping("/ResCartPut")
    public boolean postReservationCart(@RequestBody HashMap<String, String> resCart){
        Cart cart = new Cart();

        Optional<Member> searchMember = memberRepository.findByMCode(Long.parseLong(resCart.get("mid")));



        if(searchMember.isPresent()){
            Member member = searchMember.get();

            cart.setMCode(member);

            Optional<Camping> searchCamping = campingRepository.findById(Integer.parseInt(resCart.get("campingId")));
            if(searchCamping.isPresent()) {
                System.out.println("1");

                Camping camping = searchCamping.get();

                List<Cart> cartList = cartRepository.findAll();

                for(int i=0; i < cartList.size(); i++){
                    System.out.println("cartList" + i);
                    Member searchMember1 = cartList.get(i).getMCode();

                    if(searchMember1 == member){
                        Camping searchListInCamping = cartList.get(i).getCamping();

                        if(searchListInCamping == camping){
                            return false;
                        }
                    }
                }

                cart.setCamping(camping);

                cartRepository.save(cart);

            }
        }
        return true;
    }
    @PostMapping("/resCartDelete")
    public String postResCartDelete(@RequestBody HashMap<String, String> cartItemData){
        Optional<Cart> searchCart = cartRepository.findById(Integer.parseInt(cartItemData.get("cartCode")));

        if(searchCart.isPresent()){
            cartRepository.delete(searchCart.get());

            return "sc";
        }

        return "er";
    }
}