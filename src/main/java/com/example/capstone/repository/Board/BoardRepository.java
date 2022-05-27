package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    public List<Board> findByMCODE(Member member);

    @Query(value = "SELECT * FROM Board b WHERE b.PARENT_BOARD_ID = :boardId", nativeQuery = true)
    public List<Board> findByCommentId(@Param("boardId") Long boardId);

}
