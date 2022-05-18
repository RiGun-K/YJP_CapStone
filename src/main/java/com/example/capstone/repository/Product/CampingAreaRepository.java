package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.CampingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampingAreaRepository extends JpaRepository<CampingArea, Integer> {
}
