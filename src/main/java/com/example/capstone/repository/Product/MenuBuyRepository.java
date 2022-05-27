package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.PlanTag;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuBuyRepository extends JpaRepository<MenuBuy, Integer> {

    List<MenuBuy> findByMID(Member member);
    List<MenuBuy> findByMIDMID(String mid);

    @Query(value = "SELECT * FROM MENUBUY M WHERE M.kindId = :kindId", nativeQuery = true)
    List<MenuBuy> findBykindId(@Param("kindId") int kindId);

    @Query(value = "SELECT * FROM MENUBUY M JOIN KIND K ON M.kindId = K.kindid WHERE K.PARENTKINDID = :kindId", nativeQuery = true)
    List<MenuBuy> findByParentKindId(@Param("kindId") int kindId);

    @Query(value = "SELECT * FROM MENUBUY M WHERE M.BUY_NAME LIKE %:searchBuy%", nativeQuery = true)
    List<MenuBuy> findAllBysearchBuyContains(@Param("searchBuy") String searchBuy);

}
