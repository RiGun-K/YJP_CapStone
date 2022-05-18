package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.BoardMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMenuRepository extends JpaRepository<BoardMenu, Long> {

}
