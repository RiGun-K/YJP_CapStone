package com.example.capstone.repository.Storage;

import com.example.capstone.domain.storage.BoxItem;
import com.example.capstone.domain.storage.UseStorageBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoxItemRepository extends JpaRepository<BoxItem,Long> {

    @Query(value = "select * from BoxItem where useStorageBoxCode = :useBoxCode and memEquipmentCode = :memCode",nativeQuery = true)
    Optional<BoxItem> findByUseCodeAndMemCode(@Param("useBoxCode")long useBoxCode, @Param("memCode")long memCode);

    List<BoxItem> findByUseStorageBoxCode(UseStorageBox useStorageBox);
}
