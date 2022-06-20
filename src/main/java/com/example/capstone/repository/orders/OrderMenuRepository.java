package com.example.capstone.repository.orders;

import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenu, Integer> {
    OrderMenu findByOrders(Orders orders);

    @Query(value = "select ORDER_MENU.*, C2.* \n" +
            "from ORDERS join ORDER_MENU on ORDERS.ORDER_CODE = ORDER_MENU.ORDER_CODE\n" +
            "join CAMPING C2 on C2.CAMPING_ID = ORDER_MENU.CAMPING_ID\n" +
            "join CAMPINGDETAIL C3 on ORDER_MENU.DETAIL_ID = C3.DETAIL_ID and C2.CAMPING_ID = C3.CAMPING_ID\n" +
            "join MEMBER M on C2.MID = M.MCODE\n" +
            "where M.MCODE = :mcode", nativeQuery = true)
    List<OrderMenu> findBySaleMyId(@Param("mcode") Long mcode);
}