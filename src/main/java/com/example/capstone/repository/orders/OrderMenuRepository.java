package com.example.capstone.repository.orders;

import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.OrderMenuPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, OrderMenuPK> {

}
