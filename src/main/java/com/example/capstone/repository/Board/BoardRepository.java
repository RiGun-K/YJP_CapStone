package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT * FROM Board b WHERE b.PARENT_BOARD_ID = :boardId", nativeQuery = true)
    public List<Board> findByCommentId(@Param("boardId") Long boardId);

    @Query(value = "SELECT * FROM Board b WHERE b.TITLE IS NOT NULL", nativeQuery = true)
    public List<Board> findByAllList();
    @Query(value = "SELECT * FROM Board b WHERE b.TITLE IS not NULL and b.MID=:mid", nativeQuery = true)
    public List<Board> findByAllWrite(Member mid);
    @Query(value = "SELECT * FROM Board b WHERE b.TITLE IS NULL and b.MID=:mid", nativeQuery = true)
    public List<Board> findByAllComment(Member mid);

    public List<Board> findByMID(Member member);

}
