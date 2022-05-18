package com.example.capstone.repository.Product;

import com.example.capstone.domain.Product.CampingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampingAreaRepository extends JpaRepository<CampingArea, Integer> {
    @Query(value = "select * from CAMPINGAREA where PARENTAREAID is null", nativeQuery = true)
    public List<CampingArea> findByParentAreaList();

    @Query(value = "select * from CAMPINGAREA where PARENTAREAID = :areaId", nativeQuery = true)
    List<CampingArea> findCampingAreaByParentcampingarea(@Param(value = "areaId") int areaId);

}
