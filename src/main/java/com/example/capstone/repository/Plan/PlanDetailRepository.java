package com.example.capstone.repository.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {

    List<PlanDetail> findByPlanCodeAndDetailDayOrderByDetailStart(Plan planCode, int detailDay);
List<PlanDetail> findByPlanCode(Plan planCode);
}
