package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuBuyRepository extends JpaRepository<MenuBuy, Integer> {

//    List<MenuBuy> findByMID(Member member);
//    List<MenuBuy> findByMIDMID(String mid);
}
