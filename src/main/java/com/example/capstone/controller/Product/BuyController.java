package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.Product.Menu;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.dto.Product.MenuDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class BuyController {


    private static String FORMAT_YYYYMMDD = "yyyy/MM/dd"; // 1)
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD);

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

            String todayPath = LocalDateTime.now() + "_" + System.currentTimeMillis();
            String filename = todayPath + "_" + origFilename;
            System.out.println("변환된 파일명" + filename);

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


    /* 나의상품 상세페이지 */
    @GetMapping("/Buy_detail/{buyId}")
    public Optional<MenuBuy> getMyProduct_Detail(@PathVariable("buyId") int buyId) {
        System.out.println("메뉴번호 는" + buyId + "입니다.");

        Optional<MenuBuy> myMenuDetailList = menuBuyRepository.findById(buyId);
        return myMenuDetailList;

    }

    /* 구매상품 수정 */
    @PutMapping("/Buy_Update")
    public MenuBuy UpdateMyProduct_Detail(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuy menuBuy, MenuBuyDTO menuBuyDTO) throws IllegalStateException, IOException {
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

            String todayPath = LocalDateTime.now() + "_" + System.currentTimeMillis();
            String filename = todayPath + "_" + origFilename;
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
        System.out.println(menuBuyDTO.getMid());


//        List<Member> member = memberRepository.findByMID(menuDTO.getMid());
//
//        Optional<Kind> kind = kindRepository.findById(menuDTO.getKindid());
//



        menuBuy.setSavedTime(LocalDate.now().toString());

        Optional<Member> member = memberRepository.findByMID(menuBuyDTO.getMid());



        System.out.println(member.get());
        menuBuy.setMID(member.get());
        //            menu.setMID(memberRepository.findByMID("rigun").get());

        System.out.println(menuBuy);
//

        menuBuyRepository.save(menuBuy);

        return menuBuy;
    }

    /* 구매상품 삭제 */
    @DeleteMapping("/Buy_Delete/{buyId}")
    public String DeleteMyProduct_Detail(@PathVariable("buyId") int buyId) {
        System.out.println("삭제하실 뷰에서 가져온 메뉴번호는" + buyId + " 입니다.");
        Optional<MenuBuy> menuBuy = menuBuyRepository.findById(buyId);
        System.out.println("삭제하실 DB 에서 가져온 메뉴번호는" + menuBuy.get().getBuyId() + " 입니다.");


        menuBuyRepository.deleteById(buyId);
//         menuService.deleteById(menuid);
        return "메뉴가 삭제되었습니다.";
    }



    /* 조회수 증가 */
    /* 조회수를 받아와서 1 증가시키는것 까지 되었지만 repository 또는 DB에 값을 저장을 할 수 없다. */
//    @PutMapping("/product_BuyViews")
//    public MenuBuy product_BuyViews(@RequestBody HashMap<>) {
//        System.out.println(menuBuyDTO.getBuyid());
//
//            Optional<MenuBuy> menuBuy1= menuBuyRepository.findById(menuBuyDTO.getBuyid());
//        System.out.println(menuBuy1.get().getBuyViews());
//
//             menuBuy1.get().setBuyViews(menuBuy1.get().getBuyViews()+1);
//        System.out.println(menuBuy1.get());
//        menuBuyDTO.setBuyViews(menuBuy1.get().getBuyViews());
//
//        menuBuyRepository.save(menuBuyDTO);
//    }


}

