package com.example.capstone.repository.orders;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.order.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByMCode(Member MCode);

//    @Query(value = "select * from Orders where order_code = :orders", nativeQuery = true)
//    List<Orders> findByOrders(@Param("orders") Orders orders);

    @Query(value = "select ORDERS.*, ORDER_MENU.* \n" +
            "from ORDERS join ORDER_MENU on ORDERS.ORDER_CODE = ORDER_MENU.ORDER_CODE\n" +
            "join CAMPING C2 on C2.CAMPING_ID = ORDER_MENU.CAMPING_ID\n" +
            "join CAMPINGDETAIL C3 on ORDER_MENU.DETAIL_ID = C3.DETAIL_ID and C2.CAMPING_ID = C3.CAMPING_ID\n" +
            "join MEMBER M on C2.MID = M.MCODE\n" +
            "where M.MCODE = :mcode", nativeQuery = true)
    List<Orders> findBySaleMyId(@Param("mcode") Long mcode);

}
