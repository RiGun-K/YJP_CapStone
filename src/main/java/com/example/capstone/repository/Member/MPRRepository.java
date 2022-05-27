package com.example.capstone.repository.Member;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Member.MemberPreference;
import com.example.capstone.domain.Product.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MPRRepository extends JpaRepository<MemberPreference, Long> {
    List<MemberPreference> findByMember(Member member);
    Optional<MemberPreference> findByMemberAndKind(Member member, Kind kind);
}
