package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRentalRepository extends JpaRepository<MenuRental, Integer> {

    List<MenuRental> findByMID(Member member);
    List<MenuRental> findByMIDMID(String mid);
}
