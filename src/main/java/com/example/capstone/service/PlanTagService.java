package com.example.capstone.service;

import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanTag;
import com.example.capstone.repository.Plan.PlanTagRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlanTagService {

    private final PlanTagRepository planTagRepository;

    public PlanTagService(PlanTagRepository planTagRepository) {
        this.planTagRepository = planTagRepository;
    }

    public void insertTags(List<String> tags, Plan plan){
        for(int i =0; i<tags.size(); i++){
            PlanTag pt= new PlanTag();
            pt.setTagContent(tags.get(i));
            pt.setPlan(plan);
            planTagRepository.save(pt);

        }
    }
}
