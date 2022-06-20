package com.example.capstone.repository.Storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.storage.MemberEquipment;
import com.example.capstone.domain.storage.UseStorageBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberEquipmentRepository extends JpaRepository<MemberEquipment, Long> {

    List<MemberEquipment> findAllByMCode(Member member);
    List<MemberEquipment> findByUseStorageBoxCode(UseStorageBox useStorageBox);
    Optional<MemberEquipment> findByMemEquipmentCode(Long code);

    @Query(value = "select * from MEMEQUIPMENT where MCODE = :mCode and USE_STORAGE_BOX_CODE is null", nativeQuery = true)
    List<MemberEquipment> findByMemEquipment(@Param("mCode")long mCode);

}
