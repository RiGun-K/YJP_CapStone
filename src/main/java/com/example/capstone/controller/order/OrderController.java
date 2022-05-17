package com.example.capstone.controller.order;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.CampingDetail;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String postCampingRoomData(@RequestBody HashMap<String, String> reservationData) {
        Orders order = new Orders();

        Optional<Member> searchMember = memberRepository.findByMID(reservationData.get("MID"));
        if (searchMember.isPresent()) {
            Member member = searchMember.get();

            order.setMCode(member);
            order.setDeliveryGetter(reservationData.get("reservationName"));
            order.setDeliveryGetterTel(reservationData.get("reservationTel"));
            order.setDeliveryRequest(reservationData.get("reservationRequest"));
            order.setOrderPrice(Integer.parseInt(reservationData.get("orderPrice")));

            DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyyMMdd");

            order.setStartDate(LocalDate.parse(reservationData.get("startDate"), format1));
            order.setEndDate(LocalDate.parse(reservationData.get("endDate"), format1));
            order.setOrderType(reservationData.get("orderType"));
            order.setPaymentCode(reservationData.get("paymentCode"));
            order.setOrderState(reservationData.get("orderState"));

            LocalDateTime now = LocalDateTime.now();

            order.setPaymentDate(now);

            ordersRepository.save(order);


        } else {
            System.out.println("buyData Order Error");
        }

        Optional<CampingDetail> searchRoom = campingDetailRepository.findById(Integer.parseInt(reservationData.get("roomId")));
        System.out.println("구매한 상품번호는" + searchRoom);

        if (searchRoom.isPresent()) {

            CampingDetail campingDetail = searchRoom.get();
            OrderMenu orderMenu = new OrderMenu();

            orderMenu.setOrders(order);
            orderMenu.setCampingDetail(campingDetail);
            orderMenu.setOrderMenuCount(Integer.parseInt(reservationData.get("orderMenuCount")));

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

            for(int i = 0; i < buyOrdersList.size(); i++){
                Optional<Orders> searchOrder = ordersRepository.findById(buyOrdersList.get(i).getOrderCode());

                if(searchOrder.isPresent()){
                    Orders orders = searchOrder.get();
                    OrderMenu orderMenu = orderMenuRepository.findByOrders(orders);

                    MenuBuy menuBuy = orderMenu.getMenuBuy();

                    if(menuBuy == null) {
                        buyOrdersList.remove(i);
                    }
                }
            }

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

                    OrderMenu orderMenu = orderMenuRepository.findByOrders(orders);

                    MenuBuy menuBuy = orderMenu.getMenuBuy();

                    if(menuBuy != null) {
                        orderMenuList.add(i, orderMenu);
                    }
                }
            }

            System.out.println(orderMenuList.size());
            return orderMenuList;
        }
        return null;
    }


    @GetMapping("/ordersList/reservationOrders/{MCode}")
    public List<Orders> getOrderReservation(@PathVariable("MCode") Long MCode) {
        Optional<Member> searchMember = memberRepository.findById(MCode);

        if (searchMember.isPresent()) {
            Member member = searchMember.get();
            List<Orders> buyOrdersList = ordersRepository.findByMCode(member);

            for(int i = 0; i < buyOrdersList.size(); i++){
                Optional<Orders> searchOrder = ordersRepository.findById(buyOrdersList.get(i).getOrderCode());

                if(searchOrder.isPresent()){
                    Orders orders = searchOrder.get();
                    OrderMenu orderMenu = orderMenuRepository.findByOrders(orders);

                    CampingDetail campingDetail = orderMenu.getCampingDetail();

                    if(campingDetail == null) {
                        buyOrdersList.remove(i);
                    }
                }
            }

            return buyOrdersList;
        }
        return null;
    }

    @GetMapping("/ordersList/reservationOrderMenu/{MCode}")
    public List<OrderMenu> getOrderMenuReservation(@PathVariable("MCode") Long MCode) {
        Optional<Member> searchMember = memberRepository.findById(MCode);

        if (searchMember.isPresent()) {
            Member member = searchMember.get();
            List<OrderMenu> orderMenuList = new ArrayList<>();

            List<Orders> buyOrdersList = ordersRepository.findByMCode(member);

            for (int i = 0; i < buyOrdersList.size(); i++) {
                int orderCode = buyOrdersList.get(i).getOrderCode();
                Optional<Orders> searchOrder = ordersRepository.findById(orderCode);
                if (searchOrder.isPresent()) {
                    Orders orders = searchOrder.get();

                    OrderMenu orderMenu = orderMenuRepository.findByOrders(orders);

                    CampingDetail campingDetail = orderMenu.getCampingDetail();

                    if(campingDetail != null) {
                        orderMenuList.add(i, orderMenu);
                    }
                }
            }

            System.out.println(orderMenuList.size());
            return orderMenuList;
        }
        return null;
    }


}