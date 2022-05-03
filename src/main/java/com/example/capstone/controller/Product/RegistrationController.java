package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.*;
import com.example.capstone.dto.Product.CampingDTO;
import com.example.capstone.dto.Product.CampingDetailDTO;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.dto.Product.MenuRentalDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class RegistrationController {

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
            MenuBuyDTO.setSavedTime(LocalDate.now());


        MenuBuy menuBuy = new MenuBuy(MenuBuyDTO.getBuyName(), MenuBuyDTO.getBuyPrice(), MenuBuyDTO.getBuyEx(), MenuBuyDTO.getSavedTime(), MenuBuyDTO.getBuyStock(), MenuBuyDTO.getOrigFilename(), MenuBuyDTO.getFilename(), MenuBuyDTO.getFilePath(), kind.get(), member.get());
        System.out.println(menuBuy);

        menuBuyRepository.save(menuBuy);

        return menuBuy;
    }

    /* 렌탈상품 등록 페이지 */
    @PostMapping("/Rental_Signup")
    public MenuRental addMenuRental(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuRentalDTO menuRentalDTO) throws IllegalStateException, IOException {
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

            menuRentalDTO.setOrigFilename(origFilename);
            menuRentalDTO.setFilename(filename);
            menuRentalDTO.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(menuRentalDTO.getMid());
        Optional<Member> member = memberRepository.findByMID(menuRentalDTO.getMid());


        Optional<Kind> kind = kindRepository.findById(menuRentalDTO.getKindid());


        if(menuRentalDTO.getSavedTime()==null)
            menuRentalDTO.setSavedTime(LocalDate.now());


        MenuRental menuRental = new MenuRental(menuRentalDTO.getRentalName(), menuRentalDTO.getRentalPrice(), menuRentalDTO.getRentalEx(), menuRentalDTO.getSavedTime(), menuRentalDTO.getRentalStock(), menuRentalDTO.getOrigFilename(), menuRentalDTO.getFilename(), menuRentalDTO.getFilePath(), kind.get(), member.get());
        System.out.println(menuRental);

        menuRentalRepository.save(menuRental);

        return menuRental;
    }

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


        if(campingDTO.getSavedTime()==null)
            campingDTO.setSavedTime(LocalDate.now());


        Camping camping = new Camping(campingDTO.getCampingName(), campingDTO.getCampingInfo(), campingDTO.getCampingDetailState(), campingDTO.getPostalAddress(), campingDTO.getAddress(), campingDTO.getDetailAddress(), campingDTO.getSavedTime(), campingDTO.getOrigFilename(), campingDTO.getFilename(), campingDTO.getFilePath(), infoter.get(), member.get());
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


    /* 캠핑장 내 객실 등록 페이지 */
    @PostMapping("/CampingDetail_Signup")
    public CampingDetail addMenuCampingDetail(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingDetailDTO campingDetailDTO) throws IllegalStateException, IOException {
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
            campingDetailDTO.setSavedTime(LocalDate.now());


        CampingDetail campingDetail = new CampingDetail(campingDetailDTO.getDetailName(), campingDetailDTO.getDetailEx(), campingDetailDTO.getBaseNumber(), campingDetailDTO.getMaximumNumber(), campingDetailDTO.getSavedTime(), campingDetailDTO.getOrigFilename(), campingDetailDTO.getFilename(), campingDetailDTO.getFilePath(), camping.get());
        System.out.println(campingDetail);

        campingDetailRepository.save(campingDetail);

        return campingDetail;
    }
}
