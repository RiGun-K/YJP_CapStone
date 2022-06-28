package com.example.capstone.repository.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<Plan> findByPlanName(String planName);
    Optional<Plan> findByTeamCodeAndPlanName(Team team, String planName);
    List<Plan> findAllByTeamCodeTeamCode(Long team);
    List<Plan> findByTeamCode(Team teamCode);
    Page<Plan> findAll(Pageable pageable);
}
