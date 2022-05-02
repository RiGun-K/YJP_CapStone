package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.CampingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampingDetailRepository extends JpaRepository<CampingDetail, Integer> {
}
