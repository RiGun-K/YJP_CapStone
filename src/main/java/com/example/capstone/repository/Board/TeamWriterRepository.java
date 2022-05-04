package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.TeamWriter;
import com.example.capstone.domain.Board.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamWriterRepository extends JpaRepository<TeamWriter, Integer> {

}
