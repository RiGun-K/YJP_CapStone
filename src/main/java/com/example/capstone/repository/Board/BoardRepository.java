package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findByMCODE(Member member);
}

