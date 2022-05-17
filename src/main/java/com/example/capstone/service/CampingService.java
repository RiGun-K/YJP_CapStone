package com.example.capstone.service;

import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.dto.Product.CampingDTO;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.repository.Product.CampingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CampingService {

    public final CampingRepository campingRepository;

    public void countCampingView(CampingDTO campingDTO){
        Optional<Camping> findCamping = campingRepository.findById(campingDTO.getAreaId());
        System.out.println(findCamping.get().getCampingViews());
        Camping camping = findCamping.orElse(null);
        camping.setCampingViews(camping.getCampingViews()+1);
    }
}
