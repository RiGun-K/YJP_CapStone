package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRentalRepository extends JpaRepository<MenuRental, Integer> {

    List<MenuRental> findByMID(Member member);
    List<MenuRental> findByMIDMID(String mid);

    @Query(value = "SELECT * FROM MENURENTAL M WHERE M.kindId = :kindId", nativeQuery = true)
    List<MenuRental> findByrentalId(@Param("kindId") int kindId);

    @Query(value = "SELECT * FROM MENURENTAL M JOIN KIND K ON M.kindId = K.kindid WHERE K.PARENTKINDID = :kindId", nativeQuery = true)
    List<MenuRental> findByParentRentalId(@Param("kindId") int kindId);

    @Query(value = "SELECT * FROM MENURENTAL M WHERE M.RENTAL_NAME LIKE %:searchRental%", nativeQuery = true)
    List<MenuRental> findAllBysearchRentalContains(@Param("searchRental") String searchRental);

    @Query(value = "SELECT * FROM MENURENTAL M ORDER BY M.saved_time DESC", nativeQuery = true)
    List<MenuRental> findByLatest();

    @Query(value = "SELECT * FROM MENURENTAL M ORDER BY M.rental_views DESC", nativeQuery = true)
    List<MenuRental> findByViews();

}
