package com.example.capstone.repository.Storage;


import com.example.capstone.domain.storage.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByStorageName(String storageName);

    @Query(
            value = "select u.USE_STORAGE_START_TIME, u.USE_STORAGE_END_TIME, b.STORAGE_BOX_CODE, b.STORAGE_BOX_NAME,b.STORAGE_BOX_STATE, s.STORAGE_NAME, u.USE_STORAGE_BOX_CODE, u.USE_STORAGE_STATE  \n" +
                    "from STORAGE s \n" +
                    "join STORAGEBOX b on s.STORAGE_CODE = b.STORAGE_CODE \n" +
                    "join USESTORAGEBOX u on  u.STORAGE_BOX_CODE = b.STORAGE_BOX_CODE \n" +
                    "join ORDERS o on o.ORDER_CODE = u.ORDER_CODE \n" +
                    "join MEMBER m on m.MCODE = o.MID \n" +
                    "WHERE m.MID = :memberId order by s.STORAGE_NAME, b.STORAGE_BOX_STATE, u.USE_STORAGE_START_TIME ", nativeQuery = true)
    public Object[] findByMember(@Param("memberId") String memberId );
}
