package com.example.capstone.repository.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.Orders;
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

    @Query(value = "SELECT * FROM camping c ORDER BY c.saved_Time DESC", nativeQuery = true)
    public List<Camping> findAllListDesc();

    @Query(value = "SELECT * FROM camping c ORDER BY c.camping_Views DESC", nativeQuery = true)
    public List<Camping> findAllListViewDesc();

    @Query(value = "SELECT * FROM camping c WHERE c.infoterId = :infoterId", nativeQuery = true)
    public List<Camping> findByinfoterId(@Param("infoterId") int infoterId);

    @Query(value = "SELECT * FROM camping c WHERE c.areaId = :areaId", nativeQuery = true)
    public List<Camping> findByareaId(@Param("areaId") int areaId);

    @Query(value = "SELECT * FROM camping c JOIN campingArea a ON c.AREAID = a.AREA_ID \n" +
                   "WHERE a.PARENTAREAID = :areaId", nativeQuery = true)
    public List<Camping> findByareaDetailId(@Param("areaId") int areaId);


    @Query(value = "SELECT * FROM camping c WHERE c.CAMPING_NAME LIKE %:searchCamping%", nativeQuery = true)
    List<Camping> findAllBysearchCampingContains(@Param("searchCamping") String searchCamping);

}
