package com.example.capstone.repository.Plan;


import com.example.capstone.domain.Plan.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
  Optional <Team> findByTeamName(String teamName);
}