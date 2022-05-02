package com.example.capstone.controller.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.orders.OrderMenuRepository;
import com.example.capstone.repository.orders.OrdersRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
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

//    @Autowired
//    private OrderMenuRepository orderMenuRepository;

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

//            OrderMenu orderMenu = new OrderMenu();
//
//            Optional<OrderMenu> searchMenu = orderMenuRepository(시발)


        } else {
            System.out.println("buyData Error");
        }
        return "test";
    }


}
