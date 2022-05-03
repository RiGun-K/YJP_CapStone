package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.example.capstone.dto.plan.PlanTagDTO;
import com.example.capstone.service.PlanTagService;
import com.example.capstone.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class PlannerApiController {

    private final PlanService planService;
    private final PlanTagService planTagService;

    public PlannerApiController(PlanService planService, PlanTagService planTagService) {
        this.planService = planService;
        this.planTagService = planTagService;
    }


    @PostMapping("/api/createPlan")
    public Optional<Plan> createPlan(@RequestBody PlanTagDTO plan) {
    Plan newPlan= planService.createPlan(plan.getPlan());
        Optional<Plan> selectedPlan = planService.selectPlan(plan.getPlan().getPlanName());
        planTagService.insertTags(plan.getTagContentList(),newPlan);
        return selectedPlan;
    }


    @PostMapping("/api/checkPlanName")
    public String checkPlanName(@RequestBody Plan plan) {
        String result = planService.checkPlanName(plan);
        return result;
    }

    @PutMapping("/api/insertDetailPlan")
    public void insertDetailPlan(@RequestBody PlanDetail plan_detail) {
        planService.insertDetailPlan(plan_detail);
    }

    @PostMapping("/api/loadDetailPlanOfDay")
    public List<PlanDetail> loadDetailPlanOfDay(@RequestBody PlanDetail plan_detail) {
        return planService.loadDetailPlan(plan_detail);
    }

}
