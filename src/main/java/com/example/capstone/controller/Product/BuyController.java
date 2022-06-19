package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.*;
import com.example.capstone.dto.Product.ImagesDTO;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
import com.example.capstone.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class

BuyController {

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

    @Autowired
    ImagesRepository imagesRepository;
    @Autowired
    ProductService productService;


    /* 구매상품 등록 페이지 */
    @PostMapping("/Buy_Signup")
    public MenuBuy addMenuBuy(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuyDTO MenuBuyDTO) throws IllegalStateException, IOException {
        System.out.println("파일 이름" + uploadFile.getOriginalFilename());
        System.out.println("파일 크기" + uploadFile.getSize());
        try {
            String origFilename = uploadFile.getOriginalFilename();
            String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
            String filename = now + "_" + origFilename;

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

    // MultipartHttpServletRequest mhsq
    /* 구매상품에 대한 이미지 등록 */
    @PostMapping("/Buy_Signup_Files/")
    public void addMenuBuyFiles(MultipartHttpServletRequest mhsq, ImagesDTO imagesDTO) throws IllegalStateException, IOException {

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

        List<MultipartFile> mf = mhsq.getFiles("files");
        System.out.println(mf.size());
        if (mf.size() == 1 && mf.get(0).getOriginalFilename().equals("")) {

        } else {
            for (int i = 0; i < mf.size(); i++) {
                // 파일명 중복처리
                String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
                // 본래 파일명
                String origFilename = mf.get(i).getOriginalFilename();
                // DB에 저장되는 파일명
                String filename = now + "_" + origFilename;

                String filePath = savePath + "\\" + filename;
                mf.get(i).transferTo(new File(filePath));

                imagesDTO.setOrigFilename(origFilename);
                imagesDTO.setFilename(filename);
                imagesDTO.setFilePath(filePath);

                System.out.println(imagesDTO.getBuyId());
                Optional<MenuBuy> menuBuy = menuBuyRepository.findById(imagesDTO.getBuyId());


                Images images = new Images(imagesDTO.getOrigFilename(), imagesDTO.getFilename(), imagesDTO.getFilePath(), menuBuy.get());
                System.out.println(images);

                imagesRepository.save(images);
                System.out.println("파일이 저장되었습니다.");
            }

        }

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
            String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());


//            UUID uuid = UUID.randomUUID();
            String filename = now + "_" + origFilename;

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
            menuBuy.setFilePath("\\src\\frontend\\src\\assets\\"+ filename);


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

    /* 구매상품 조회 시 조회수 증가 */
    @PostMapping("/Buy_countView")
    public void countCampingView(@RequestBody HashMap<String, String> buyId){
        System.out.println(buyId.get("a"));
        Optional<MenuBuy> menuBuy = menuBuyRepository.findById(Integer.parseInt(buyId.get("a")));
        menuBuy.get().setBuyViews(menuBuy.get().getBuyViews()+1);
        menuBuyRepository.save(menuBuy.get());
    }

    /* 캠핑장 종류별 조회하기 ( 캠핑 ) */
    @GetMapping("/product_detail_buy/{kindId}")
    public List<MenuBuy> menuBuys(@PathVariable("kindId") int kindId) {
        System.out.println("상품번호는 " + kindId);
        if(kindId == 1 || kindId == 2) {
            List<MenuBuy> menuBuys = menuBuyRepository.findByParentKindId(kindId);
            System.out.println(menuBuys);
            return menuBuys;
        } else {
            List<MenuBuy> menuBuys = menuBuyRepository.findBykindId(kindId);
            System.out.println(menuBuys);
            return menuBuys;
        }


    }
    
    /* 구매상품 검색 */
    @GetMapping("/search_BuyList")
    public List<MenuBuy> searchBuyList(@RequestParam("searchBuy") String searchBuy) {
//        List<MenuBuy> menuBuys = productService.searchBuy(searchBuy);
        List<MenuBuy> menuBuys = menuBuyRepository.findAllBysearchBuyContains(searchBuy);
        System.out.println("결과는" + menuBuys);
        return menuBuys;

    }

    /* 구매상품 최신순 */
    @GetMapping("/product_ByBuyLatest")
    public List<MenuBuy> menuBuysByLatest() {
        List<MenuBuy> menus = menuBuyRepository.findByLatest();
        System.out.println(menus);
        return menus;
    }

    /* 구매상품 조회순 */
    @GetMapping("/product_ByBuyViews")
    public List<MenuBuy> menuBuysByViews() {
        List<MenuBuy> menus = menuBuyRepository.findByViews();
        System.out.println(menus);
        return menus;
    }

    /* 구매상품 낮은 가격순 */
    @GetMapping("/product_ByBuyLowPrice")
    public List<MenuBuy> menuBuysByLowPrice() {
        List<MenuBuy> menus = menuBuyRepository.findByLowPrice();
        System.out.println(menus);
        return menus;
    }

    /* 구매상품 높은 가격순 */
    @GetMapping("/product_ByBuyHighPrice")
    public List<MenuBuy> menuBuysByHighPrice() {
        List<MenuBuy> menus = menuBuyRepository.findByHighPrice();
        System.out.println(menus);
        return menus;
    }
}
