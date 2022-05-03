package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.Camping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampingRepository extends JpaRepository<Camping, Integer> {

    Optional<Camping> findByCampingName(String campingName);
}
