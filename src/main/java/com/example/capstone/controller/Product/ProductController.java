package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.dto.Product.MenuDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
//@CrossOrigin("*")
//@RequiredArgsConstructor
//@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
//@AllArgsConstructor  // @Autowired 대신사용 , But 쓰면 DB 저장안됨 ( Null )
@RequestMapping("/api")
public class ProductController {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MenuBuyRepository menuBuyRepository;

    @Autowired
    KindRepository kindRepository;

    @Autowired
    ProductService productService;


    /* 전체상품 리스트 */
    @GetMapping("/product_list")
//    @JsonProperty("menu")
    public List<MenuBuy> menuList() {
        List<MenuBuy> menus = menuBuyRepository.findAll();
        System.out.println(menus);
//        List<Menu> menuList = menuRepository.findAllByMemberList();

//        if (menus.isEmpty()) {
//            return null;
//        } else {
//            for (Menu m : menus) {
//                System.out.println(m.getKind().getKindname());
//                // return m.getKind().getKindname();
//            }
//        }
//        return menus;
        return menus;
    }

    /* 판매상품 상세 페이지 */
    @GetMapping("/product_detail/{menuid}")
    public Optional<MenuBuy> getProduct_Detail(@PathVariable("menuid") int menuid) {
        System.out.println("메뉴번호 는" + menuid + "입니다.");

        Optional<MenuBuy> menuDetailList = menuBuyRepository.findById(menuid);
        return menuDetailList;

    }

    /* 나의상품 리스트 */
//    @GetMapping("/myProduct_list/{userid}")
////    @JsonProperty("menu")
//    public List<Menu> getMenuMyList(@RequestBody long userid) {
//
////        System.out.println(menuList.get(0).getKindid().getKindname());
////        List<Menu> myList = menuRepository.findAllByMenuList("dongmin");
//        System.out.println(userid + " 분 의 상품을 조회하겠습니다.");
//        Member user = productService.findByMemberId(userid);
////        List<Member> member = memberRepository.findByMID(menuDTO.getMid());
//        List<Menu> menuMyList = menuRepository.findByMID(user);
////        List<Member> members = memberRepository.findById(member);
//
//        return menuMyList;
//    }





    /* 나의상품 수정 */
//    @PutMapping("/myProduct_Update/{menuid}")
//    public String UpdateMyProduct_Detail(ProductDetailDTO productDetailDTO) {
//        productService.savePost(productDetailDTO);
//        return "게시글이 수정되었습니다";
//    }




}
