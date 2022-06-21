package com.example.capstone.repository.orders;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "select * from Cart where buy_Id is not null and MCode = :member", nativeQuery = true)
    List<Cart> findByMenuBuy(@Param("member") Member member);

    @Query(value = "select * from Cart where rental_Id is not null and MCode = :member", nativeQuery = true)
    List<Cart> findByMenuRental(@Param("member") Member member);

    @Query(value = "select * from Cart where camping_id is not null and MCode = :member", nativeQuery = true)
    List<Cart> findByCamping(@Param("member") Member member);
}