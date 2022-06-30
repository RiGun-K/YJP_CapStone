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
    Optional<MemberEquipment> findByMemEquipmentCode(Long code);

    @Query(value = "select m.* from MEMEQUIPMENT m \n" +
            "left join BOXITEM b on m.MEM_EQUIPMENT_CODE = b.MEM_EQUIPMENT_CODE \n" +
            "where MCODE = 2 and b.MEM_EQUIPMENT_CODE  is null or m.MEM_EQUIPMENT_COUNT > b.BOX_ITEM_COUNT", nativeQuery = true)
    List<MemberEquipment> findByMemEquipment(@Param("mCode")long mCode);

}
