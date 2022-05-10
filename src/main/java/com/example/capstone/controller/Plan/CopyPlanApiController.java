package com.example.capstone.controller.Plan;


import com.example.capstone.dto.plan.CopyPlanDto;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.PlanTagDto;
import com.example.capstone.service.CopyPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor

public class CopyPlanApiController {

    private final CopyPlanService copyPlanService;


    @PutMapping("/api/createCopyPlan")
    public void createCopyPlan(@RequestBody CopyPlanDto copyPlanDto){
    copyPlanService.createCopyPlan(copyPlanDto);

    }
}
