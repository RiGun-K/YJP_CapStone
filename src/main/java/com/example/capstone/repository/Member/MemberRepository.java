package com.example.capstone.repository.Member;

import com.example.capstone.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByMID(String MID);
    public Optional<Member> findByMNick(String MNICK);

}
