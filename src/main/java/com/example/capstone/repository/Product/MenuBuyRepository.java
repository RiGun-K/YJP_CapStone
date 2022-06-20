package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.PlanTag;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.OrderMenu;
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

    @Query(value = "SELECT * FROM MENUBUY M ORDER BY M.saved_time DESC", nativeQuery = true)
    List<MenuBuy> findByLatest();

    @Query(value = "SELECT * FROM MENUBUY M ORDER BY M.buy_views DESC", nativeQuery = true)
    List<MenuBuy> findByViews();

    @Query(value = "SELECT * FROM MENUBUY M ORDER BY M.buy_price", nativeQuery = true)
    List<MenuBuy> findByLowPrice();

    @Query(value = "SELECT * FROM MENUBUY M ORDER BY M.buy_price DESC", nativeQuery = true)
    List<MenuBuy> findByHighPrice();


    @Query(value = "select ORDER_MENU.*, B.* \n" +
            "from ORDERS join ORDER_MENU on ORDERS.ORDER_CODE = ORDER_MENU.ORDER_CODE \n" +
            "join MENUBUY B on B.BUY_ID = ORDER_MENU.MENU_BUY_ID \n" +
            "join MEMBER M on M.MID = M.MCODE \n" +
            "where M.MCODE = :mcode", nativeQuery = true)
    List<MenuBuy> findBySaleMyId(@Param("mcode") Long mcode);
}
