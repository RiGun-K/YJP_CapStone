package com.example.capstone.repository.Storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.storage.MemberEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberEquipmentTest extends JpaRepository<MemberEquipment, Long> {
    List<MemberEquipment> findByMCode(Member mcode);
}
