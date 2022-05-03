package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.dto.Product.MenuDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.repository.Product.MenuRepository;
import com.example.capstone.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
//@CrossOrigin("*")
//@RequiredArgsConstructor
//@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
//@AllArgsConstructor  // @Autowired 대신사용
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
    @GetMapping("/myProduct_list/{user}")
    public List<MenuBuy> myMenuList(@PathVariable("user") String user) {
        List<MenuBuy> myMenus = menuBuyRepository.findByMIDMID(user);

//        List<Menu> myMenus = menuRepository.findAll();
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
        return myMenus;
        // 한 행만 출력시 Optional , 전체 행은 List
    }

    /* 나의상품 상세페이지 */
    @GetMapping("/myProduct_detail/{menuid}")
    public Optional<MenuBuy> getMyProduct_Detail(@PathVariable("menuid") int menuid) {
        System.out.println("메뉴번호 는" + menuid + "입니다.");

        Optional<MenuBuy> myMenuDetailList = menuBuyRepository.findById(menuid);
        return myMenuDetailList;

    }

    /* 나의상품 삭제 */
    @DeleteMapping("/myProduct_Delete/{menuid}")
    public String DeleteMyProduct_Detail(@PathVariable("menuid") int menuid) {
        System.out.println("삭제하실 메뉴번호는" + menuid + " 입니다.");
        Optional<MenuBuy> menuBuy = menuBuyRepository.findById(menuid);

        menuBuyRepository.deleteById(menuid);
        // menuService.deleteById(menuid);
        return "메뉴가 삭제되었습니다.";
    }


    /* 나의상품 수정 */
//    @PutMapping("/myProduct_Update/{menuid}")
//    public String UpdateMyProduct_Detail(ProductDetailDTO productDetailDTO) {
//        productService.savePost(productDetailDTO);
//        return "게시글이 수정되었습니다";
//    }

    @PutMapping("/myProduct_Update")
    public MenuBuy UpdateMyProduct_Detail(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuy menuBuy, MenuDTO menuDTO) throws IllegalStateException, IOException {
        System.out.println("파일 이름" + uploadFile.getOriginalFilename());
        System.out.println("파일 크기" + uploadFile.getSize());

//        Optional<Menu> updateMyMenu = menuRepository.findById(menu.getMenuid());
//        updateMyMenu.get().setMID(menu.getMID());
//        updateMyMenu.get().setEx(menu.getEx());
//        updateMyMenu.get().setMenuname(menu.getMenuname());
//        updateMyMenu.get().setPrice(menu.getPrice());
//        updateMyMenu.get().setStock(menu.getStock());
//        updateMyMenu.get().setKindid(menu.getKindid());

//        menuRepository.save(updateMyMenu.get());
        try {
            String origFilename = uploadFile.getOriginalFilename();

            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + origFilename;
            /* 실행되는 위치의 'files' 폴더에 파일이 저장 */
            String savePath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets";
            /* 파일이 저장되는 폴더가 없으면 폴더 생성 */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            menuBuy.setOrigFilename(origFilename);
            menuBuy.setFilename(filename);
            menuBuy.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("123");
        }
        System.out.println(menuBuy.getKindid());
        System.out.println(menuBuy.getBuyId());

        /* menu.getMID() 하면 반환값이 String인데 타입은 Menu라서 Null로 뜸
           따라서 menuDTO.getMid() <- String 타입으로 넘겨서 member 테이블에서 'rigun'을 찾아줌
         */
        System.out.println(menuDTO.getMid());


//        List<Member> member = memberRepository.findByMID(menuDTO.getMid());
//
//        Optional<Kind> kind = kindRepository.findById(menuDTO.getKindid());
//



        menuBuy.setSavedTime(LocalDate.now());

        Optional<Member> member = memberRepository.findByMID(menuDTO.getMid());

        System.out.println(member.get());
        menuBuy.setMID(member.get());
        //            menu.setMID(memberRepository.findByMID("rigun").get());

        System.out.println(menuBuy);
//

        menuBuyRepository.save(menuBuy);

        return menuBuy;
    }



}
