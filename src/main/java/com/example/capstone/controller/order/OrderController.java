package com.example.capstone.controller.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.MenuRepository;
import com.example.capstone.repository.orders.OrderMenuRepository;
import com.example.capstone.repository.orders.OrdersRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderMenuRepository orderMenuRepository;

    

    @PostMapping("/buyData")
    public String postBuyData(@RequestBody HashMap<String, String> buyData){
        Orders order = new Orders();

        Optional<Member> searchMember = memberRepository.findByMID(buyData.get("MID"));
        if(searchMember.isPresent()) {
            Member member = searchMember.get();
            order.setMCode(member);
            order.setDeliveryZipcode(buyData.get("deliveryZipcode"));
            order.setDeliveryAddress(buyData.get("deliveryAddress"));
            order.setDeliveryGetter(buyData.get("deliveryGetter"));
            order.setDeliveryGetterTel(buyData.get("deliveryGetterTel"));
            order.setDeliveryRequest(buyData.get("deliveryRequest"));
            order.setOrderPrice(Integer.parseInt(buyData.get("orderPrice")));
            order.setOrderType(buyData.get("orderType"));
            order.setPaymentCode(buyData.get("paymentCode"));
            order.setOrderState(buyData.get("orderState"));

            LocalDateTime now = LocalDateTime.now();

            order.setPaymentDate(now);

            ordersRepository.save(order);



        } else {
            System.out.println("buyData Order Error");
        }

        Optional<Menu> searchMenu = menuRepository.findById(Integer.parseInt(buyData.get("menuId")));

        if(searchMenu.isPresent()) {

            Menu menu = searchMenu.get();
            OrderMenu orderMenu = new OrderMenu();

            orderMenu.setOrders(order);
            orderMenu.setMenu(menu);
            orderMenu.setOrderMenuCount(Integer.parseInt(buyData.get("orderMenuCount")));;

            orderMenuRepository.save(orderMenu);

        }else{
            System.out.println("buyData Menu Error");
        }
        return "test";
    }

    @GetMapping("/ordersList/buyOrders/{MCode}")
    public List<Orders> getOrderBuy(@PathVariable("MCode") Long MCode) {
        System.out.println("회원번호는" + MCode + "입니다.");

        Optional<Member> searchMember = memberRepository.findById(MCode);

        if(searchMember.isPresent()) {
            Member member = searchMember.get();
            List<Orders> buyOrdersList = ordersRepository.findByMCode(member);

            System.out.println(buyOrdersList);
            return buyOrdersList;
        }
        return null;
    }

    @GetMapping("/ordersList/buyOrderMenu/{MCode}")
    public List<OrderMenu> getOrderMenuBuy(@PathVariable("MCode") Long MCode){
        Optional<Member> searchMember = memberRepository.findById(MCode);

        if(searchMember.isPresent()){
            Member member = searchMember.get();
            List<OrderMenu> orderMenuList = new ArrayList<>();

            List<Orders> buyOrdersList = ordersRepository.findByMCode(member);

            for(int i = 0; i < buyOrdersList.size(); i++){
                int orderCode = buyOrdersList.get(i).getOrderCode();
                Optional<Orders> searchOrder = ordersRepository.findById(orderCode);
                if(searchOrder.isPresent()){
                    Orders orders = searchOrder.get();

                    OrderMenu orderMenu = orderMenuRepository.findByOrders(orders);

                    System.out.println(orderMenu.getOrderMenuId() + ", " + orderMenu.getOrderMenuCount());

                    orderMenuList.add(i, orderMenu);
                }
            }
            System.out.println(orderMenuList);
            return orderMenuList;
        }
        return null;
    }
}
