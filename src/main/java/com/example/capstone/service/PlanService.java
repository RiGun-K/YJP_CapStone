package com.example.capstone.service;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.example.capstone.domain.Plan.PlanTag;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.dto.plan.PlanDetailDto;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.TeamDto;
import com.example.capstone.repository.Plan.PlanDetailRepository;
import com.example.capstone.repository.Plan.PlanRepository;
import com.example.capstone.repository.Plan.PlanTagRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor

public class PlanService {

    public final TeamRepository teamRepository;
    public final PlanRepository planRepository;
    public final PlanDetailRepository planDetailRepository;
    private final PlanTagRepository planTagRepository;


    public Plan createPlan(Plan plan) {
        planRepository.save(plan);
        return plan;
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

    public void insertDetailPlan(PlanDetailDto plan_detaildto) {
        //planDetailDto에서 PlanDto를 꺼낸다.
        PlanDto planDto = plan_detaildto.getPlanCode();
        // PlanDto를 DB에서 조회하여 찾는다.
        Optional<Plan> findPlan = planRepository.findById(planDto.getPlanCode());
        Plan planEntity = findPlan.orElse(null);
        // DB에서 조회되지 않으면 끝
        if (planEntity == null) return;

        // PlanDetail를 저장하기위해 엔티티를 생성
        PlanDetail planDetail = PlanDetail.builder()
                .detailCode(plan_detaildto.getDetailCode())
                .checklists(plan_detaildto.getChecklists())
                .planCode(planEntity)
                .detailName(plan_detaildto.getDetailName())
                .detailMemo(plan_detaildto.getDetailMemo())
                .detailStart(plan_detaildto.getDetailStart())
                .detailEnd(plan_detaildto.getDetailEnd())
                .detailDay(plan_detaildto.getDetailDay()).build();
        // PlanDtail 저장
        planDetailRepository.save(planDetail);
    }

    public List<PlanDetailDto> loadDetailPlan(PlanDetailDto plan_detail) {
        Optional<Plan> findPlan = planRepository.findById(plan_detail.getPlanCode().getPlanCode());
        Plan planEntity = findPlan.orElse(null);
        if (planEntity == null) return null;

        List<PlanDetail> planDetails = planDetailRepository.findByPlanCodeAndDetailDayOrderByDetailStart(planEntity, plan_detail.getDetailDay());
        List<PlanDetailDto> planDetailDtos = new ArrayList<>();

        for (PlanDetail pd : planDetails) {

            PlanDetailDto planDetailDto = PlanDetailDto.builder()
                    .detailDay(pd.getDetailDay())
                    .detailEnd(pd.getDetailEnd())
                    .detailCode(pd.getDetailCode())
                    .detailMemo(pd.getDetailMemo())
                    .detailName(pd.getDetailName())
                    .detailStart(pd.getDetailStart())
                    .checklists(pd.getChecklists())
                    .planCode(new PlanDto(pd.getPlanCode()))
                    .build();
            System.out.println(planDetailDto.toString());
            System.out.println(planDetailDto);
            System.out.println(planDetailDto);
            System.out.println(planDetailDto);
            planDetailDtos.add(planDetailDto);
        }
        System.out.println("sssssssssssssss");System.out.println("sssssssssssssss");System.out.println("sssssssssssssss");

        return planDetailDtos;
    }

    public List<PlanDto> loadAllPlans() {

        List<Plan> plans = planRepository.findAll();
        List<PlanDto> planDtos = new ArrayList<>();

        for (Plan plan : plans) {
            planDtos.add(plan.toPlanDto());
        }


        return planDtos;
    }
    public void countView(PlanDto planDto){
        Optional<Plan> findPlan = planRepository.findById(planDto.getPlanCode());
        Plan plan = findPlan.orElse(null);
        plan.setPlanViews(plan.getPlanViews()+1);
    }

    public void deleteDetailPlan(Long planDetailCode){
        Optional<PlanDetail> planDetail = planDetailRepository.findById(planDetailCode);
        System.out.println(planDetailCode+"삭제준비");
        planDetailRepository.delete(planDetail.get());
        System.out.println("삭제완료");
    }
    public void deletePlan(Long planCode){
        Optional<Plan> plan = planRepository.findById(planCode);
        List<PlanDetail> planDetails = planDetailRepository.findByPlanCode(plan.get());
        List<PlanTag> planTags = planTagRepository.findByPlan(plan.get());
        for(PlanDetail pd : planDetails){
            planDetailRepository.delete(pd);
        }
        for(PlanTag pt : planTags){
            planTagRepository.delete(pt);
        }
        planRepository.delete(plan.get());
    }
}
