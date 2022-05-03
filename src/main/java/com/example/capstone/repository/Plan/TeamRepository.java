package com.example.capstone.repository.Plan;


import com.example.capstone.domain.Plan.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

  // 1 . 팀테이블에서 하나를 찾겟따
  // 2 . teamname을 찾겠다
  public Optional <Team> findByTeamName(String teamName);

  // 1. 우리는 팀마스터를 찾아야된다
  public Optional <Team> findByTeamMaster(String teamMaster);




}