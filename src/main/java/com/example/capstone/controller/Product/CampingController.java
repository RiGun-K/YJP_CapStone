package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.*;
import com.example.capstone.dto.Product.CampingDTO;
import com.example.capstone.dto.Product.ImagesDTO;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
import com.example.capstone.service.CampingService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/api")
public class CampingController {

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
    CampingAreaRepository campingAreaRepository;

    @Autowired
    CampingService campingService;
    @Autowired
    ImagesRepository imagesRepository;


    @GetMapping("/campingRound")
    private List<CampingArea> getRound() {

        List<CampingArea> campingAreaList = campingAreaRepository.findByParentAreaList();

        return campingAreaList;
    }

    @GetMapping("/campingSmallRound/{parentId}")
    private List<CampingArea> getSmallRound(@PathVariable(value = "parentId") int parentId) {

        List<CampingArea> campingAreaList = campingAreaRepository.findCampingAreaByParentcampingarea(parentId);

        return campingAreaList;
    }

    /* 캠핑장 등록 페이지 */
    @PostMapping("/Camping_Signup")
    public Camping addMenuCamping(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingDTO campingDTO) throws IllegalStateException, IOException {
        System.out.println("파일 이름" + uploadFile.getOriginalFilename());
        System.out.println("파일 크기" + uploadFile.getSize());

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
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            campingDTO.setOrigFilename(origFilename);
            campingDTO.setFilename(filename);
            campingDTO.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----1--------");
        System.out.println(campingDTO.getMid());
        //
        Optional<Member> member = memberRepository.findByMID(campingDTO.getMid());

        System.out.println("----2--------");
        System.out.println(campingDTO.getInfoterId());

        System.out.println("-----3-------");
        Optional<Infoter> infoter = infoterRepository.findById(campingDTO.getInfoterId());
        System.out.println(infoter.get());

        System.out.println("-----4-------");
        Optional<CampingArea> campingArea = campingAreaRepository.findById(campingDTO.getAreaId());
        System.out.println(campingArea.get());


        System.out.println("-----외래키 값들 시작-------");
        System.out.println(member.get());
        System.out.println(infoter.get());
        System.out.println(campingArea.get());
        System.out.println("-----외래키 값들 끝-------");
        if(campingDTO.getSavedTime()==null)
            campingDTO.setSavedTime(LocalDate.now().toString());


        Camping camping = new Camping(campingDTO.getCampingName(), campingDTO.getCampingInfo(), campingDTO.getCampingDetailState(), campingDTO.getPostalAddress(), campingDTO.getAddress(), campingDTO.getDetailAddress(), campingDTO.getSavedTime(), campingDTO.getOrigFilename(), campingDTO.getFilename(), campingDTO.getFilePath(), infoter.get(), campingArea.get(), member.get());
        System.out.println(camping);

        campingRepository.save(camping);

        return camping;
    }

    /* 캠핑장에 대한 이미지 등록 */
    @PostMapping("/Camping_Signup_Files/")
    public void addMenuCampingFiles(MultipartHttpServletRequest mhsq, ImagesDTO imagesDTO) throws IllegalStateException, IOException {

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

                System.out.println(imagesDTO.getCampingId());
                Optional<Camping> camping = campingRepository.findById(imagesDTO.getCampingId());


                Images images = new Images(imagesDTO.getOrigFilename(), imagesDTO.getFilename(), imagesDTO.getFilePath(), camping.get());
                System.out.println(images);

                imagesRepository.save(images);
                System.out.println("파일이 저장되었습니다.");
            }

        }

    }

    /* 캠핑장 내 객실 등록전 캠핑아이디 받아오기 */
    @GetMapping("/CampingDetail_BeforeSignup/{campingName}")
    public Optional<Camping> getBeforeSingup(@PathVariable("campingName") String campingName) {
        System.out.println("객실 등록할 캠핑장 이름은" + campingName + "입니다.");

        Optional<Camping> myRegistrationCampingName = campingRepository.findByCampingName(campingName);

        return myRegistrationCampingName;

    }

    /* 캠핑장 조회 페이지 */
    @GetMapping("/Camping_List/{user}")
    public List<Camping> myMenuList(@PathVariable("user") String user) {
        List<Camping> myMenus = campingRepository.findByMIDMID(user);

        return myMenus;
        // 한 행만 출력시 Optional , 전체 행은 List
    }


    /* 캠핑장 상세페이지 */
    @GetMapping("/Camping_Detail/{campingId}")
    public Optional<Camping> getMyProduct_Detail(@PathVariable("campingId") int campingId) {
        System.out.println("캠핑장 번호 는" + campingId + "입니다.");

        Optional<Camping> myMenuDetailList = campingRepository.findById(campingId);
        return myMenuDetailList;

    }

    /* 캠핑장 수정 */
    @PutMapping("/Camping_Update")
    public Camping UpdateMyProduct_Detail(@RequestParam(value = "file", required = false) MultipartFile uploadFile, Camping camping, CampingDTO campingDTO) throws IllegalStateException, IOException {
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

            camping.setOrigFilename(origFilename);
            camping.setFilename(filename);
            camping.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("123");
        }
        System.out.println(camping.getAreaId());
        System.out.println(camping.getMID());
        System.out.println(camping.getInfoterId());

        /* menu.getMID() 하면 반환값이 String인데 타입은 Menu라서 Null로 뜸
           따라서 menuDTO.getMid() <- String 타입으로 넘겨서 member 테이블에서 'rigun'을 찾아줌
         */

//        List<Member> member = memberRepository.findByMID(menuDTO.getMid());
//
//        Optional<Kind> kind = kindRepository.findById(menuDTO.getKindid());
//

        System.out.println("----1--------");
        Optional<Member> member = memberRepository.findByMID(campingDTO.getMid());

        System.out.println("-----2-------");
        Optional<Infoter> infoter = infoterRepository.findById(campingDTO.getInfoterId());

        System.out.println("-----3-------");
        Optional<CampingArea> campingArea = campingAreaRepository.findById(campingDTO.getAreaId());


        System.out.println(member.get());
        System.out.println(infoter.get());
        System.out.println(campingArea.get());

        System.out.println("-----4-------");

        camping.setMID(member.get());
        camping.setInfoterId(infoter.get());
        camping.setAreaId(campingArea.get());
        //            menu.setMID(memberRepository.findByMID("rigun").get());

        camping.setSavedTime(LocalDate.now().toString());

        campingRepository.save(camping);

        return camping;
    }

    /* 캠핑장 삭제 */
    @DeleteMapping("/Camping_Delete/{campingId}")
    public String DeleteMyProduct_Detail(@PathVariable("campingId") int campingId) {
        System.out.println("삭제하실 뷰에서 가져온 메뉴번호는" + campingId + " 입니다.");
        Optional<Camping> menuCamping = campingRepository.findById(campingId);
        System.out.println("삭제하실 DB 에서 가져온 메뉴번호는" + menuCamping.get().getCampingId() + " 입니다.");


        campingRepository.deleteById(campingId);
//         menuService.deleteById(menuid);
        return "메뉴가 삭제되었습니다.";
    }

    /* 캠핑장 조회 시 조회수 증가 */
    @PostMapping("/Camping_countView")
    public void countCampingView(@RequestBody HashMap<String, String> campingId){
        System.out.println(campingId.get("a"));
        Optional<Camping> campingF = campingRepository.findById(Integer.parseInt(campingId.get("a")));
        campingF.get().setCampingViews(campingF.get().getCampingViews()+1);
        campingRepository.save(campingF.get());
    }

}
