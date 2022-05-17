package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampingRepository extends JpaRepository<Camping, Integer> {

    Optional<Camping> findByCampingName(String campingName);

    List<Camping> findByMID(Member member);
    List<Camping> findByMIDMID(String mid);

    @Query(value = "SELECT * FROM camping c WHERE c.infoterId = :infoterId", nativeQuery = true)
    public List<Camping> findByinfoterId(@Param("infoterId") int infoterId);
}
