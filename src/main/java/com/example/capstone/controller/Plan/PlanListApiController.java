package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.service.PlanService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class PlanListApiController {

    private final PlanService planService;

    public PlanListApiController(PlanService planService) {
        this.planService = planService;
    }


    @GetMapping("api/loadAllPlans")
    public List<Plan> loadAllPlans(){
        return planService.loadAllPlans();
    }
}
