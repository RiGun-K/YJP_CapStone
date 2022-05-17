package com.example.capstone.controller.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.CampingDetail;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingDetailRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
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
    private MenuBuyRepository menuBuyRepository;

    @Autowired
    private CampingDetailRepository campingDetailRepository;

    @Autowired
    private OrderMenuRepository orderMenuRepository;


    /* 구매상품 결제 */
    @PostMapping("/buyData")
    public String postBuyData(@RequestBody HashMap<String, String> buyData) {
        Orders order = new Orders();

        Optional<Member> searchMember = memberRepository.findByMID(buyData.get("MID"));
        if (searchMember.isPresent()) {
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

        Optional<MenuBuy> searchMenu = menuBuyRepository.findById(Integer.parseInt(buyData.get("menuId")));
        System.out.println("구매한 상품번호는" + searchMenu);

        if (searchMenu.isPresent()) {

            MenuBuy menuBuy = searchMenu.get();
            OrderMenu orderMenu = new OrderMenu();

            orderMenu.setOrders(order);
            orderMenu.setMenuBuy(menuBuy);
            orderMenu.setOrderMenuCount(Integer.parseInt(buyData.get("orderMenuCount")));
            ;

            orderMenuRepository.save(orderMenu);

        } else {
            System.out.println("buyData Menu Error");
        }
        return "test";
    }

    /* 캠핑장 객실 예약 */
    @PostMapping("/CampingRoomData")
    public String postCampingRoomData(@RequestBody HashMap<String, String> buyData) {
        Orders order = new Orders();

        Optional<Member> searchMember = memberRepository.findByMID(buyData.get("MID"));
        if (searchMember.isPresent()) {
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

        // 129 - null 뜸
        Optional<CampingDetail> searchMenu = campingDetailRepository.findById(Integer.parseInt(buyData.get("menuId")));
        System.out.println("구매한 상품번호는" + searchMenu);

        if (searchMenu.isPresent()) {

            CampingDetail campingDetail = searchMenu.get();
            OrderMenu orderMenu = new OrderMenu();

            orderMenu.setOrders(order);
            orderMenu.setCampingDetail(campingDetail);
            orderMenu.setOrderMenuCount(Integer.parseInt(buyData.get("orderMenuCount")));
            ;

            orderMenuRepository.save(orderMenu);

        } else {
            System.out.println("buyData Menu Error");
        }
        return "test";
    }

    @GetMapping("/ordersList/buyOrders/{MCode}")
    public List<Orders> getOrderBuy(@PathVariable("MCode") Long MCode) {
        System.out.println("회원번호는" + MCode + "입니다.");

        Optional<Member> searchMember = memberRepository.findById(MCode);
        System.out.println(searchMember);

        if (searchMember.isPresent()) {
            Member member = searchMember.get();
            List<Orders> buyOrdersList = ordersRepository.findByMCode(member);

            System.out.println(buyOrdersList);
            return buyOrdersList;
        }
        return null;
    }

    @GetMapping("/ordersList/buyOrderMenu/{MCode}")
    public List<OrderMenu> getOrderMenuBuy(@PathVariable("MCode") Long MCode) {
        Optional<Member> searchMember = memberRepository.findById(MCode);
        System.out.println("회원번호는" + MCode + "입니다.");

        if (searchMember.isPresent()) {
            Member member = searchMember.get();
            List<OrderMenu> orderMenuList = new ArrayList<>();

            List<Orders> buyOrdersList = ordersRepository.findByMCode(member);

            for (int i = 0; i < buyOrdersList.size(); i++) {
                int orderCode = buyOrdersList.get(i).getOrderCode();
                Optional<Orders> searchOrder = ordersRepository.findById(orderCode);
                if (searchOrder.isPresent()) {
                    Orders orders = searchOrder.get();

                    System.out.println(orders.getOrderCode());

                    OrderMenu orderMenu = orderMenuRepository.findByOrders(orders);

                    System.out.println(orderMenu);

                    orderMenuList.add(i, orderMenu);
                }
            }
            System.out.println(orderMenuList);
            return orderMenuList;
        }
        return null;
    }

}