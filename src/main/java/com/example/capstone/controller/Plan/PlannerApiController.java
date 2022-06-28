package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.example.capstone.dto.plan.PlanDetailDto;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.PlanTagDto;
import com.example.capstone.service.PlanTagService;
import com.example.capstone.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor

public class PlannerApiController {

    private final PlanService planService;
    private final PlanTagService planTagService;



    @PostMapping("/api/createPlan")
    public Optional<Plan> createPlan(@RequestParam(value = "file", required = false) MultipartFile uploadFile, PlanTagDto plan) {
        System.out.println("------------------------");
        System.out.println(plan.getPlan().getPlanName());

        System.out.println("ㅁ잉"+ uploadFile.getSize());
        System.out.println(plan.getPlan().getPlanName());
        System.out.println("여기까지는 되나??");
    Plan newPlan= planService.createPlan(plan.getPlan());

        Optional<Plan> selectedPlan = planService.selectPlan(plan.getPlan().getPlanName());
        planTagService.insertTags(plan.getTagContentList(),newPlan);
        return selectedPlan;
    }
    @PutMapping("api/editPlan")
    public PlanDto editPlan(@RequestBody PlanDto plan){
        return planService.editPlan(plan);
    }


    @PostMapping("/api/checkPlanName")
    public String checkPlanName(@RequestBody Plan plan) {
        String result = planService.checkPlanName(plan);
        return result;
    }

    @PutMapping("/api/insertDetailPlan")
    public void insertDetailPlan(@RequestBody PlanDetailDto plan_detail) {
        System.out.println("plan_detail = " + plan_detail);
        planService.insertDetailPlan(plan_detail);
    }

    @PostMapping("/api/loadDetailPlanOfDay")
    public List<PlanDetailDto> loadDetailPlanOfDay(@RequestBody PlanDetailDto plan_detail) {
        return planService.loadDetailPlan(plan_detail);
    }
    @DeleteMapping("api/deleteDetailPlan")
    public void deleteDetailPlan(@RequestParam Long planDetailCode){
        planService.deleteDetailPlan(planDetailCode);
    }

}
