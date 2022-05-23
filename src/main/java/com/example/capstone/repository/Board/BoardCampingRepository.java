package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.BoardCamping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCampingRepository extends JpaRepository<BoardCamping, Long> {

    @Query(value = "SELECT * FROM BOARD_CAMPING WHERE CAMPINGID = :campingId", nativeQuery = true)
    public List<BoardCamping> findBycampingId(@Param("campingId") int campingId);

}
