package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.MenuRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRentalRepository extends JpaRepository<MenuRental, Integer> {
}
