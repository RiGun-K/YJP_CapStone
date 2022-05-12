package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.MenuBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampingRepository extends JpaRepository<Camping, Integer> {

    Optional<Camping> findByCampingName(String campingName);

    List<Camping> findByMID(Member member);
    List<Camping> findByMIDMID(String mid);
}
