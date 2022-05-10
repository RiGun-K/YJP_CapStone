package com.example.capstone.repository.orders;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByMCode(Member MCode);

}
