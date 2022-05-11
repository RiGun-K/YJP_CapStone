package com.example.capstone.repository.Storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.storage.MemberEquipment;
import com.example.capstone.domain.storage.UseStorageBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberEquipmentRepository extends JpaRepository<MemberEquipment, Long> {

    List<MemberEquipment> findAllByMCode(Member member);
    List<MemberEquipment> findByUseStorageBoxCode(UseStorageBox useStorageBox);
}
