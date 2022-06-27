package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.dto.plan.PlanDto;
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

//asd
    @GetMapping("api/loadAllPlans")
    public List<PlanDto> loadAllPlans() {
        return planService.loadAllPlans();
    }

    @GetMapping("api/tagFilter")
    public List<PlanDto> tagFilter(@RequestParam("tagContent") String tagContent) {
        List<PlanDto> plans = planTagService.tagFilter(tagContent);
        return plans;
    }
    @PutMapping("/api/countView")
    public void countView(@RequestBody PlanDto plan){
        System.out.println(plan);
        planService.countView(plan);
    }
}
