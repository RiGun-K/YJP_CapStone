package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
}
