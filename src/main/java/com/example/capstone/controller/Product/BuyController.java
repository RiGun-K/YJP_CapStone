package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class BuyController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MenuBuyRepository menuBuyRepository;
    @Autowired
    MenuRentalRepository menuRentalRepository;
    @Autowired
    CampingRepository campingRepository;
    @Autowired
    KindRepository kindRepository;
    @Autowired
    InfoterRepository infoterRepository;
    @Autowired
    CampingDetailRepository campingDetailRepository;

    /* 구매상품 등록 페이지 */
    @PostMapping("/Buy_Signup")
    public MenuBuy addMenuBuy(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuyDTO MenuBuyDTO) throws IllegalStateException, IOException {
        System.out.println("파일 이름" + uploadFile.getOriginalFilename());
        System.out.println("파일 크기" + uploadFile.getSize());

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
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            MenuBuyDTO.setOrigFilename(origFilename);
            MenuBuyDTO.setFilename(filename);
            MenuBuyDTO.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(MenuBuyDTO.getMid());
        Optional<Member> member = memberRepository.findByMID(MenuBuyDTO.getMid());


        Optional<Kind> kind = kindRepository.findById(MenuBuyDTO.getKindid());


        if(MenuBuyDTO.getSavedTime()==null)
            MenuBuyDTO.setSavedTime(LocalDate.now().toString());


        MenuBuy menuBuy = new MenuBuy(MenuBuyDTO.getBuyName(), MenuBuyDTO.getBuyPrice(), MenuBuyDTO.getBuyEx(), MenuBuyDTO.getSavedTime(), MenuBuyDTO.getBuyStock(), MenuBuyDTO.getOrigFilename(), MenuBuyDTO.getFilename(), MenuBuyDTO.getFilePath(), kind.get(), member.get());
        System.out.println(menuBuy);

        menuBuyRepository.save(menuBuy);

        return menuBuy;
    }

    /* 구매상품 조회 페이지 */
    @GetMapping("/Buy_List/{user}")
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
}
