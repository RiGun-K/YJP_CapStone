package com.example.capstone.repository.Plan;

import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamBoardRepository extends JpaRepository<TeamBoard,Long> {

    List<TeamBoard> findByTeamCodeOrderByBoardDate(Team team);
    List<TeamBoard> findByTeamCode(Team team);
}
