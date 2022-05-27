package com.example.capstone.service;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.repository.Product.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final MemberRepository MemberRepository;
    private MemberRepository memberRepository;

    private MenuRepository menuRepository;
    private MenuBuyRepository menuBuyRepository;

    @Autowired
    public ProductService(MemberRepository memberRepository, MenuRepository menuRepository) {
        this.MemberRepository = memberRepository;
        this.menuRepository = menuRepository;
    }


    public Member findByMemberId(long userid){
        Optional<Member> memberfind = memberRepository.findById(userid);
        return memberfind.get();
    }

    public List<MenuBuy> searchBuy(String searchBuy) {
        List<MenuBuy> menuBuys = menuBuyRepository.findAllBysearchBuyContains(searchBuy);
        List<MenuBuy> menus = new ArrayList<>();
        for(MenuBuy menuBuy : menuBuys) {
            System.out.println("검색한 구매상품은 " + menuBuy);
            
        }
        return menus;
    }

//    @Transactional
//    public int savePost(MenuDTO menuDTO) {
//        return menuRepository.save(menuDTO.toEntity()).getMenuid();
//
//    }
}
