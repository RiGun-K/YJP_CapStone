package com.example.capstone.repository.Storage;


import com.example.capstone.domain.storage.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {


}
