package com.example.capstone.repository.Storage;

import com.example.capstone.domain.storage.Storage;
import com.example.capstone.domain.storage.StorageBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorageBoxRepository extends JpaRepository<StorageBox, Long> {

    public List<StorageBox> findByStorageCodeStorageCode(long storageBoxCode);




    @Query(value = "select * from STORAGEBOX WHERE STORAGE_CODE = :storage AND STORAGE_BOX_NAME= :boxName", nativeQuery = true)
    public Optional<StorageBox> findByStorageCodeAndStorageBoxName(@Param("storage")long storage, @Param("boxName")String boxName);

    @Query(value = "select m.MNICK, b.*,u.USE_STORAGE_BOX_CODE,u.USE_STORAGE_STATE from STORAGEBOX b \n" +
            "join USESTORAGEBOX u on u.STORAGE_BOX_CODE = b.STORAGE_BOX_CODE\n" +
            "join MEMBER m on u.MCODE = m.MCODE \n" +
            "where b.STORAGE_BOX_CODE = :storageBoxCode and sysdate between u.USE_STORAGE_START_TIME and u.USE_STORAGE_END_TIME or not u.USE_STORAGE_STATE = 1", nativeQuery = true)
    public Object[] findByBoxInformation(@Param("storageBoxCode")long storageBoxCode);

    @Query(value = "select * from STORAGE s\n"+
            "join STORAGEBOX b on s.STORAGE_CODE = b.STORAGE_CODE \n" +
            "where s.STORAGE_NAME = :storageName and b.STORAGE_BOX_NAME = :boxName",nativeQuery = true)
    public Optional<StorageBox> findByStorageNameAndStorageBoxName(@Param("storageName")String storageName, @Param("boxName")String boxName);
}
