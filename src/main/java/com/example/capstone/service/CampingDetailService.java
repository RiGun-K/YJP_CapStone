package com.example.capstone.service;

import com.example.capstone.domain.Product.Camping;
import com.example.capstone.repository.Product.CampingDetailRepository;
import com.example.capstone.repository.Product.CampingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
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
