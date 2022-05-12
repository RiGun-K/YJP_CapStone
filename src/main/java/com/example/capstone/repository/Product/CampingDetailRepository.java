package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.CampingDetail;
import com.example.capstone.domain.Product.MenuRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampingDetailRepository extends JpaRepository<CampingDetail, Integer> {

    List<CampingDetail> findByCampingId(Camping campingId);

}
