package com.example.capstone.repository.Storage;

import com.example.capstone.domain.storage.RepairItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepairItemRepository extends JpaRepository<RepairItem, Long> {

    @Query(value = "select r.* from REPAIRITEM r join BOXITEM b on r.BOX_ITEM_CODE = b.BOX_ITEM_CODE \n" +
            "where b.USE_STORAGE_BOX_CODE = :useCode",nativeQuery = true)
    List<RepairItem> findByBoxItemCode(@Param("useCode") long useCode);
}
