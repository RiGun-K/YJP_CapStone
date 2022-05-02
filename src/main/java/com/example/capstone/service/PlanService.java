package com.example.capstone.service;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.example.capstone.repository.Plan.PlanDetailRepository;
import com.example.capstone.repository.Plan.PlanRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PlanService {

    public final TeamRepository teamRepository;
    public final PlanRepository planRepository;
    public final PlanDetailRepository plan_detailRepository;


    public Plan createPlan(Plan plan) {

        planRepository.save(plan);

        return plan;
    }

    public PlanService(TeamRepository teamRepository, PlanRepository planRepository, PlanDetailRepository plan_detailRepository) {
        this.teamRepository = teamRepository;
        this.planRepository = planRepository;
        this.plan_detailRepository = plan_detailRepository;
    }

    public String checkPlanName(Plan plan) {

        Optional<Plan> check = planRepository.findByTeamCodeAndPlanName(plan.getTeamCode(), plan.getPlanName());
        if (check.isPresent() == true) {
            return "n";
        } else {
            return "y";
        }
    }

    public Optional<Plan> selectPlan(String planName) {
        Optional<Plan> selectPlan = planRepository.findByPlanName(planName);
        return selectPlan;
    }

    public void insertDetailPlan(PlanDetail plan_detail) {
        plan_detailRepository.save(plan_detail);


    }

    public List<PlanDetail> loadDetailPlan(PlanDetail plan_detail) {
        List<PlanDetail> planDetails = plan_detailRepository.findByPlanCodeAndDetailDay(plan_detail.getPlanCode(), plan_detail.getDetailDay());
        return planDetails;
    }

    public List<Plan> loadAllPlans(){
        return planRepository.findAll();
    }

}
