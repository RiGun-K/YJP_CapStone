package com.example.capstone.repository.orders;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {
    OrderMenu findByOrders(Orders orders);
}