package com.example.capstone.service;


import com.example.capstone.domain.Plan.Checklist;
import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.example.capstone.dto.plan.ChecklistDto;
import com.example.capstone.repository.Plan.ChecklistRepository;
import com.example.capstone.repository.Plan.PlanDetailRepository;
import com.example.capstone.repository.Plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final PlanDetailRepository planDetailRepository;
    private final PlanRepository planRepository;

    public void insertChecklist(Checklist checklist) {
        checklistRepository.save(checklist);
    }

    public List<Checklist> loadChecklist(Checklist checklist) {
        List<Checklist> ch = checklistRepository.findByDetailCode(checklist.getDetailCode());
        return ch;
    }

    public ChecklistDto updateState(Checklist checklistCode) {
        System.out.println(checklistCode);
        Optional<Checklist> cl = checklistRepository.findById(checklistCode.getChecklistCode());
        if (cl.get().getCheckState() == 'y') {
            cl.get().setCheckState('n');
            checklistRepository.save(cl.get());
        } else if (cl.get().getCheckState() == 'n') {
            cl.get().setCheckState('y');
            checklistRepository.save(cl.get());
        }
        ChecklistDto a = new ChecklistDto();
        a.setChecklistCode(cl.get().getChecklistCode());
        a.setCheckContent(cl.get().getCheckContent());
        a.setDetailCode(cl.get().getDetailCode().getDetailCode());
        a.setCheckState(cl.get().getCheckState());
        System.out.println(a.getDetailCode());
        return a;
    }

    public void deleteChecklist(Checklist checklist) {
        checklistRepository.findById(checklist.getChecklistCode());
        checklistRepository.deleteById(checklist.getChecklistCode());
    }

    public List<ChecklistDto> loadAllCheckList(Long planCode) {
        Optional<Plan> plan = planRepository.findById(planCode);
        List<PlanDetail> pd = planDetailRepository.findByPlanCode(plan.get());
        List<ChecklistDto> checklists = new ArrayList<>();

        for (PlanDetail planDetails : pd) {
            List<Checklist> checklists1 = checklistRepository.findByDetailCode(planDetails);
            for (Checklist i : checklists1) {
                ChecklistDto a = new ChecklistDto();
                a.setChecklistCode(i.getChecklistCode());
                a.setCheckContent(i.getCheckContent());
                a.setDetailCode(i.getDetailCode().getDetailCode());
                a.setCheckState(i.getCheckState());
                checklists.add(a);
            }
        }
        return checklists;
    }
}
