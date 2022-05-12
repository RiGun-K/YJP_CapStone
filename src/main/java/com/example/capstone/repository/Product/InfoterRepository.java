package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.Infoter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoterRepository extends JpaRepository<Infoter, Integer> {

}
