package com.example.capstone.service;


import com.example.capstone.domain.Plan.Checklist;
import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.example.capstone.dto.plan.CopyPlanDto;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.repository.Plan.ChecklistRepository;
import com.example.capstone.repository.Plan.CopyPlanRepository;
import com.example.capstone.repository.Plan.PlanDetailRepository;
import com.example.capstone.repository.Plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CopyPlanService {

    private final PlanRepository planRepository;
    private final PlanDetailRepository planDetailRepository;
    private final ChecklistRepository checklistRepository;
    private final EntityManager em;

    public Optional<PlanDto> createCopyPlan(CopyPlanDto copyPlanDto) {
        Optional<Plan> oldPlan = planRepository.findById(copyPlanDto.getOldPlanDto().getPlanCode());
        if (oldPlan.isEmpty()) {
            return null;
        }
       Plan oldPlanCode =oldPlan.get();

        em.detach(oldPlan.get());
        oldPlan.get().setPlanName(copyPlanDto.getPlanDto().getPlanName());
        oldPlan.get().setPlanStart(copyPlanDto.getPlanDto().getPlanStart());
        oldPlan.get().setPlanEnd(copyPlanDto.getPlanDto().getPlanEnd());
        oldPlan.get().setPlanDestination(copyPlanDto.getPlanDto().getPlanDestination());
        oldPlan.get().setPlanType(copyPlanDto.getPlanDto().getPlanType());
        oldPlan.get().setPlanNumber(copyPlanDto.getPlanDto().getPlanNumber());
        oldPlan.get().setPlanBudget(copyPlanDto.getPlanDto().getPlanBudget());
        oldPlan.get().setPlanTotalDate(copyPlanDto.getPlanDto().getPlanTotalDate());
        oldPlan.get().setPlanOpen(copyPlanDto.getPlanDto().getPlanOpen());
        List<PlanDetail> planDetail = planDetailRepository.findByPlanCode(oldPlanCode);
        oldPlan.get().setPlanCode(null);
        em.persist(oldPlan.get());

        for (int i = 0; i < planDetail.size(); i++) {
            em.detach(planDetail.get(i));
            List<Checklist> checklists = checklistRepository.findByDetailCode(planDetail.get(i));
            planDetail.get(i).setPlanCode(oldPlan.get());
            planDetail.get(i).setDetailCode(null);
            em.persist(planDetail.get(i));
            for(int j=0; j<checklists.size();j++){
                em.detach(checklists.get(j));
                checklists.get(j).setDetailCode(planDetail.get(i));
                checklists.get(j).setChecklistCode(null);
                em.persist(checklists.get(j));
            }
        }

        Optional<PlanDto> pd= Optional.ofNullable(oldPlan.get().toPlanDto());

        return pd;
    }
}
