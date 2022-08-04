package com.example.capstone.repository.Storage;


import com.example.capstone.domain.storage.Storage;
import com.example.capstone.domain.storage.StorageBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByStorageName(String storageName);
    Optional<Storage> findByFilename(String filename);


    @Query(value = "select * from STORAGE where STORAGE_STATE = 0 order by STORAGE_NAME",nativeQuery = true)
    List<Storage> findByNotState();

//    @Query(value = "select s.STORAGE_CODE, s.STORAGE_NAME, b.STORAGE_BOX_CODE, b.STORAGE_BOX_NAME, b.STORAGE_BOX_STATE, u.USE_STORAGE_BOX_CODE, u.USE_STORAGE_STATE \n" +
//            "from STORAGE s\n" +
//            "join STORAGEBOX b on  s.STORAGE_CODE = b.STORAGE_CODE \n" +
//            "left outer join USESTORAGEBOX u on u.STORAGE_BOX_CODE = b.STORAGE_BOX_CODE \n" +
//            "left outer join MEMBER m on m.MCODE = u.MCODE \n" +
//            "WHERE m.MID = :memberId and u.USE_STORAGE_STATE = '2' or sysdate between u.USE_STORAGE_START_TIME and u.USE_STORAGE_END_TIME \n" +
//            "order by s.STORAGE_NAME, b.STORAGE_BOX_CODE ", nativeQuery = true)
//    public Object[] findByMember(@Param("memberId") String memberId );
//
@Query(value = "select s.STORAGE_CODE as storageCode, s.STORAGE_NAME as storageName, b.STORAGE_BOX_CODE as storageBoxCode, \n" +
        "b.STORAGE_BOX_NAME as storageBoxName, b.STORAGE_BOX_STATE as storageBoxState, u.USE_STORAGE_BOX_CODE as useStorageBoxCode, u.USE_STORAGE_STATE as useStorageState \n" +
        "from STORAGE s \n" +
        "join STORAGEBOX b on  s.STORAGE_CODE = b.STORAGE_CODE \n" +
        "left outer join USESTORAGEBOX u on u.STORAGE_BOX_CODE = b.STORAGE_BOX_CODE \n" +
        "left outer join MEMBER m on m.MCODE = u.MCODE \n" +
        "WHERE m.MID = :memberId and u.USE_STORAGE_STATE = '2' or sysdate between u.USE_STORAGE_START_TIME and u.USE_STORAGE_END_TIME \n" +
        "order by s.STORAGE_NAME, b.STORAGE_BOX_CODE ", nativeQuery = true)
public List<Map<String, Object>> findByMember(@Param("memberId") String memberId );

    @Query( value = "select * from STORAGE where STORAGE_ADDRESS like :areaName%", nativeQuery = true)
    public List<Storage> findByStorageAddress(@Param("areaName")String areaName);

    @Query(value = "select * from STORAGE s join STORAGEBOX b on s.STORAGE_CODE =b.STORAGE_CODE where STORAGE_BOX_CODE = :boxCode",nativeQuery = true)
    Optional<Storage> findByStorageBoxCode(@Param("boxCode")long boxCode);
}