package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KindRepository extends JpaRepository<Kind, Integer> {
    //
    public List<Kind> findAll();
    public Optional<Kind> findByKindname(String kindname);

    @Query(value = "select * from Kind where PARENTKINDID=20",nativeQuery = true)
    List<Kind> findByRepairGroupList();
}
