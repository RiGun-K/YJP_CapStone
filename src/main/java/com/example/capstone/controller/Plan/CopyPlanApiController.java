package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.dto.plan.CopyPlanDto;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.PlanTagDto;
import com.example.capstone.service.CopyPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor

public class CopyPlanApiController {

    private final CopyPlanService copyPlanService;


    @PutMapping("/api/createCopyPlan")
    public Optional<PlanDto> createCopyPlan(@RequestBody CopyPlanDto copyPlanDto){
   return copyPlanService.createCopyPlan(copyPlanDto);
    }

    @PutMapping("api/countUsed")
    public void countUsed(@RequestBody PlanDto planDto){
        copyPlanService.countUsed(planDto);
    }
}
