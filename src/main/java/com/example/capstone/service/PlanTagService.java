package com.example.capstone.service;

import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanTag;
import com.example.capstone.repository.Plan.PlanTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class PlanTagService {

    private final PlanTagRepository planTagRepository;

    public void insertTags(List<String> tags, Plan plan) {
        for (int i = 0; i < tags.size(); i++) {
            PlanTag pt = new PlanTag();
            pt.setTagContent(tags.get(i));
            pt.setPlan(plan);
            planTagRepository.save(pt);

        }
    }

    public List<Plan> tagFilter(String tagContent) {
        List<PlanTag> planTags = planTagRepository.findAllByTagContent(tagContent);
        List<Plan> plans = new ArrayList<>();
        for (PlanTag planTag : planTags) {
            System.out.println("planTag = " + planTag);
            Plan plan = planTag.getPlan();
            plans.add(plan);
        }

//        planTags.stream().map(planTag -> planTag.getPlan()).forEach(plan -> plans.add(plan));
        return plans;
    }
}
