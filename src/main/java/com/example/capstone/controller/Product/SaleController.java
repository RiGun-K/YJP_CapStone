package com.example.capstone.controller.Product;

import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.repository.Product.CampingRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.repository.Product.MenuRentalRepository;
import com.example.capstone.repository.orders.OrderMenuRepository;
import com.example.capstone.repository.orders.OrdersRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class SaleController {

    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderMenuRepository orderMenuRepository;
    @Autowired
    CampingRepository campingRepository;
    @Autowired
    MenuBuyRepository menuBuyRepository;
    @Autowired
    MenuRentalRepository menuRentalRepository;

    /* 캠핑장 판매상품 조회 */
    @GetMapping("/Sale_MyCamping/{mcode}")
    public List<Orders> myRoomDetail(@PathVariable("mcode") Long mcode) {
        List<Orders> mySaleOrderList = ordersRepository.findBySaleMyId(mcode);

        return mySaleOrderList;
    }

    @GetMapping("/Sale_MyCampingDetail/{mcode}")
    public List<OrderMenu> myRoomDetails(@PathVariable("mcode") Long mcode) {
        List<OrderMenu> mySaleOrderDetailList = orderMenuRepository.findBySaleMyId(mcode);

        return mySaleOrderDetailList;
    }

    //

    @GetMapping("/Sale_MyBuy/{mcode}")
    public List<MenuBuy> myRoomDetailss(@PathVariable("mcode") Long mcode) {
        List<MenuBuy> mySaleBuyList = menuBuyRepository.findBySaleMyId(mcode);

        return mySaleBuyList;
    }

    @GetMapping("/Sale_MyRental/{mcode}")
    public List<MenuRental> myRoomDetailsss(@PathVariable("mcode") Long mcode) {
        List<MenuRental> mySaleBuyList = menuRentalRepository.findBySaleMyId(mcode);

        return mySaleBuyList;
    }
}
