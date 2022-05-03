package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.service.PlanService;
import com.example.capstone.service.PlanTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PlanListApiController {

    private final PlanService planService;
    private final PlanTagService planTagService;


    @GetMapping("api/loadAllPlans")
    public List<Plan> loadAllPlans() {
        return planService.loadAllPlans();
    }

    @GetMapping("api/tagFilter")
    public List<Plan> tagFilter(@RequestParam("TagContent") String TagContent) {

        List<Plan> plans = planTagService.tagFilter(TagContent);

        return plans;
    }
}
