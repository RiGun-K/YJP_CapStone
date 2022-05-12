package com.example.capstone.service;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class ProductService {
    private final MemberRepository MemberRepository;
    private MemberRepository memberRepository;

    private MenuRepository menuRepository;

    @Autowired
    public ProductService(MemberRepository memberRepository, MenuRepository menuRepository) {
        this.MemberRepository = memberRepository;
        this.menuRepository = menuRepository;
    }


    public Member findByMemberId(long userid){
        Optional<Member> memberfind = memberRepository.findById(userid);
        return memberfind.get();
    }

//    @Transactional
//    public int savePost(MenuDTO menuDTO) {
//        return menuRepository.save(menuDTO.toEntity()).getMenuid();
//
//    }
}
