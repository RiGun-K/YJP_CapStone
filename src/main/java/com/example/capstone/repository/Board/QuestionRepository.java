package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Board.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Board, Long> {

    @Query(value = "SELECT * FROM Question q WHERE q.TITLE IS NOT NULL", nativeQuery = true)
    public List<Question> findByAllList();
}
