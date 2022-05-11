package com.example.capstone.service;

import com.example.capstone.domain.Product.Camping;
import com.example.capstone.repository.Product.CampingDetailRepository;
import com.example.capstone.repository.Product.CampingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CampingDetailService {
    private CampingDetailRepository campingDetailRepository;
    private CampingRepository campingRepository;

    @Autowired
    CampingDetailService(CampingDetailRepository campingDetailRepository, CampingRepository campingRepository) {
        this.campingDetailRepository = campingDetailRepository;
        this.campingRepository = campingRepository;
    }

    public Camping findByCampingId(int campingId){
        Optional<Camping> campingidfind = campingRepository.findById(campingId);
        return campingidfind.get();
    }
}
