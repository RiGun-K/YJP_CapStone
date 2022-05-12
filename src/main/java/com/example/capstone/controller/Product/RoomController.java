package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.CampingDetail;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import com.example.capstone.dto.Product.CampingDTO;
import com.example.capstone.dto.Product.CampingDetailDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
import com.example.capstone.service.CampingDetailService;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class RoomController {

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
    CampingDetailService campingDetailService;


    /* 캠핑장 내 객실 등록 페이지 */
    @PostMapping("/CampingDetail_Signup")
    public CampingDetail addMenuCampingDetail(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingDetailDTO campingDetailDTO) throws IllegalStateException, IOException {
        System.out.println("파일 이름" + uploadFile.getOriginalFilename());
        System.out.println("파일 크기" + uploadFile.getSize());

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
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            campingDetailDTO.setOrigFilename(origFilename);
            campingDetailDTO.setFilename(filename);
            campingDetailDTO.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----1--------");
        System.out.println(campingDetailDTO.getCampingId());
        Optional<Camping> camping = campingRepository.findById(campingDetailDTO.getCampingId());
        System.out.println("----2--------");
        System.out.println(camping.get());
        //

        if(campingDetailDTO.getSavedTime()==null)
            campingDetailDTO.setSavedTime(LocalDate.now().toString());


        CampingDetail campingDetail = new CampingDetail(campingDetailDTO.getDetailName(), campingDetailDTO.getDetailPrice(), campingDetailDTO.getBaseNumber(), campingDetailDTO.getMaximumNumber(), campingDetailDTO.getDetailFunction(), campingDetailDTO.getSavedTime(), campingDetailDTO.getOrigFilename(), campingDetailDTO.getFilename(), campingDetailDTO.getFilePath(), camping.get());
        System.out.println(campingDetail);

        campingDetailRepository.save(campingDetail);

        return campingDetail;
    }


    /* 캠핑장을 통한 객실상품 조회 페이지 */
    @GetMapping("/Room_List/{campingId}")
    public List<CampingDetail> myRoomList(@PathVariable("campingId") Integer campingId, CampingDetailDTO campingDetailDTO) {
        System.out.println("캠핑장 번호는" + campingId);
        System.out.println("캠핑장 번호는" + campingDetailDTO.getCampingId());

        /* 한 테이블에서 참조하는 외래키의 값을 가진 모든 행들을 조회한다
           외래키 = 객체 이므로 타입을 변환할 필요가 있다.
           
           1. 먼저 부모(PK) 인 캠핑테이블에서 찾는다.
           2. 그 찾은 부모(PK) 값을 찾고자하는 테이블에서 외래키에 (1.)값을 넣어서 찾는다.
         */
        Camping camping = campingDetailService.findByCampingId(campingId);
        System.out.println("캠핑장 번호는" + camping);
        System.out.println("캠핑장 번호는" + camping.getCampingId());

        List<CampingDetail> myMenus = campingDetailRepository.findByCampingId(camping);

        return myMenus;
    }

    /* 각각의 객실 정보조회 */
    @GetMapping("/Room_Detail/{detailId}")
    public Optional<CampingDetail> myRoomDetail(@PathVariable("detailId") Integer detailId) {
        System.out.println("객실 번호는" + detailId);
        Optional<CampingDetail> myDetailList = campingDetailRepository.findById(detailId);

        return myDetailList;
    }

    /* 객실 수정 페이지 */
    @PutMapping("/Room_Update")
    public CampingDetail updateCampingDetail(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingDetail campingDetail, CampingDetailDTO campingDetailDTO) throws IllegalStateException, IOException {
        System.out.println("파일 이름" + uploadFile.getOriginalFilename());
        System.out.println("파일 크기" + uploadFile.getSize());

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
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            campingDetail.setOrigFilename(origFilename);
            campingDetail.setFilename(filename);
            campingDetail.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("----객실 아이디--------");
        System.out.println(campingDetailDTO.getDetailId());
        System.out.println("----캠핑장 아이디--------");
        Optional<Camping> camping = campingRepository.findById(campingDetailDTO.getCampingId());
        System.out.println(camping.get());


        campingDetail.setSavedTime(LocalDate.now().toString());

        campingDetail.setCampingId(camping.get());

        campingDetailRepository.save(campingDetail);

        return campingDetail;
    }

    /* 객실 삭제 */
    @DeleteMapping("/Room_Delete/{detailId}")
    public String DeleteMyProduct_Detail(@PathVariable("detailId") int detailId) {
        System.out.println("삭제하실 뷰에서 가져온 객실번호는" + detailId + " 입니다.");
        Optional<CampingDetail> campingDetail  = campingDetailRepository.findById(detailId);
        System.out.println("삭제하실 DB 에서 가져온 객실번호는" + campingDetail.get().getDetailId() + " 입니다.");

        campingDetailRepository.delete(campingDetail.get());
//         menuService.deleteById(menuid);
        return "메뉴가 삭제되었습니다.";
    }
}
