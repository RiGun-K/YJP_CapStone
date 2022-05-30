package com.example.capstone.repository.Storage;

import com.example.capstone.domain.order.Orders;
import com.example.capstone.domain.storage.StorageBox;
import com.example.capstone.domain.storage.UseStorageBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UseStorageBoxRepository extends JpaRepository<UseStorageBox, Long> {
    List<UseStorageBox> findByStorageBoxCode(StorageBox storageBox);
    List<UseStorageBox> findByOrderCode(Orders orders);
    @Query(value = "select * from USESTORAGEBOX where USE_STORAGE_STATE = :state",nativeQuery = true)
    Optional<UseStorageBox> findByUseStorageState(@Param("state")String state);

    @Query(value = "select s.STORAGE_CODE, s.STORAGE_NAME, b.STORAGE_BOX_CODE, b.STORAGE_BOX_NAME\n" +
            "from STORAGE s\n" +
            "join STORAGEBOX b on s.STORAGE_CODE = b.STORAGE_CODE\n" +
            "join USESTORAGEBOX u on b.STORAGE_BOX_CODE = u.STORAGE_BOX_CODE\n" +
            "where u.USE_STORAGE_BOX_CODE = :usbCode",nativeQuery = true)
    public Object[] findByInFo(@Param("usbCode")long usbCode);

    @Query(value = "select u.USE_STORAGE_START_TIME, u.USE_STORAGE_END_TIME \n" +
            "from USESTORAGEBOX u \n" +
            "join STORAGEBOX b on b.STORAGE_BOX_CODE = u.STORAGE_BOX_CODE \n" +
            "join STORAGE s on s.STORAGE_CODE = b.STORAGE_CODE \n" +
            "where s.STORAGE_CODE = :storageCode and b.STORAGE_BOX_CODE = :boxCode and u.MCODE = :mcode \n"+
            "order by u.USE_STORAGE_START_TIME", nativeQuery = true)
    public List<List<String>> findByUseTimes(@Param("storageCode") long stCode, @Param("boxCode") long boxCode, @Param("mcode") long mcode);
}
