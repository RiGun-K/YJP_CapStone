package com.example.capstone.repository.Plan;

import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamBoardRepository extends JpaRepository<TeamBoard,Long> {

    Page<TeamBoard> findByTeamCodeOrderByBoardDate(Team team, Pageable pageable);
    List<TeamBoard> findByTeamCode(Team team);
}
