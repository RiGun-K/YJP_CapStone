package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.Camping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampingRepository extends JpaRepository<Camping, Integer> {

}
