package com.example.capstone.controller.Cart;

import com.example.capstone.domain.order.Orders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class CartController {
    @PostMapping("/buyData")
    public void buyDataPost(@RequestBody HashMap<String, String> orderPay){
        Orders order = new Orders();
    }
}
