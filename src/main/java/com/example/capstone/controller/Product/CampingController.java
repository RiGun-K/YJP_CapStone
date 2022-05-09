package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.CampingArea;
import com.example.capstone.domain.Product.Infoter;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.dto.Product.CampingDTO;
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

    /* 캠핑장 등록 페이지 */
    @PostMapping("/Camping_Signup")
    public Camping addMenuCamping(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingDTO campingDTO) throws IllegalStateException, IOException {
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

        System.out.println("-----4-------");
        Optional<CampingArea> campingArea = campingAreaRepository.findById(campingDTO.getAreaId());



        if(campingDTO.getSavedTime()==null)
            campingDTO.setSavedTime(LocalDate.now().toString());


        Camping camping = new Camping(campingDTO.getCampingName(), campingDTO.getCampingInfo(), campingDTO.getCampingDetailState(), campingDTO.getPostalAddress(), campingDTO.getAddress(), campingDTO.getDetailAddress(), campingDTO.getSavedTime(), campingDTO.getOrigFilename(), campingDTO.getFilename(), campingDTO.getFilePath(), infoter.get(), campingArea.get(), member.get());
        System.out.println(camping);

        campingRepository.save(camping);

        return camping;
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
}
