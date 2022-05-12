package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Writer, Integer> {


}

