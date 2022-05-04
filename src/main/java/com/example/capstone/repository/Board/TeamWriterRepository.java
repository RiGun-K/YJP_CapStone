package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.TeamWriter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamWriterRepository extends JpaRepository<TeamWriter, Integer> {

//    public Optional <TeamWriter> findById(String teamwriter_code);

}
