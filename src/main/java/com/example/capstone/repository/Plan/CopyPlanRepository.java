package com.example.capstone.repository.Plan;

import com.example.capstone.domain.Plan.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyPlanRepository extends JpaRepository<Plan, Long> {
}
