package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.TeamWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamWriterRepository extends JpaRepository<TeamWriter, Integer> {

}
