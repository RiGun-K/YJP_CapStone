package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import com.example.capstone.dto.Product.MenuRentalDTO;
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
public class RentalController {

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
            menuRentalDTO.setSavedTime(LocalDate.now().toString());


        MenuRental menuRental = new MenuRental(menuRentalDTO.getRentalName(), menuRentalDTO.getRentalPrice(), menuRentalDTO.getRentalEx(), menuRentalDTO.getSavedTime(), menuRentalDTO.getRentalStock(), menuRentalDTO.getOrigFilename(), menuRentalDTO.getFilename(), menuRentalDTO.getFilePath(), kind.get(), member.get());
        System.out.println(menuRental);

        menuRentalRepository.save(menuRental);

        return menuRental;
    }

    /* 렌탈상품 조회 페이지 */
    @GetMapping("/Rental_List/{user}")
    public List<MenuRental> myMenuList(@PathVariable("user") String user) {
        List<MenuRental> myMenus = menuRentalRepository.findByMIDMID(user);

        return myMenus;
    }

    /* 렌탈상품 상세조회 페이지 */
    @GetMapping("/Rental_detail/{rentalId}")
    public Optional<MenuRental> myMenuLists(@PathVariable("rentalId") int rentalId) {
        Optional<MenuRental> myMenus = menuRentalRepository.findById(rentalId);

        return myMenus;
    }

    /* 렌탈상품 수정 페이지 */
    @PutMapping("/Rental_Update")
    public MenuRental updateMenuRental(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuRental menuRental, MenuRentalDTO menuRentalDTO) throws IllegalStateException, IOException {
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

            menuRental.setOrigFilename(origFilename);
            menuRental.setFilename(filename);
            menuRental.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(menuRentalDTO.getMid());
        Optional<Member> member = memberRepository.findByMID(menuRentalDTO.getMid());


//        Optional<Kind> kind = kindRepository.findById(menuRentalDTO.getKindid());


        menuRental.setSavedTime(LocalDate.now().toString());


        System.out.println(menuRental);
        menuRental.setMID(member.get());
//        menuRental.setKindid(kind.get());


        menuRentalRepository.save(menuRental);

        return menuRental;
    }

    /* 렌탈 상품 삭제 */
    @DeleteMapping("/Rental_Delete/{rentalId}")
    public String DeleteMyProduct_Detail(@PathVariable("rentalId") int rentalId) {
        System.out.println("삭제하실 뷰에서 가져온 메뉴번호는" + rentalId + " 입니다.");
        Optional<MenuRental> menuRental = menuRentalRepository.findById(rentalId);
        System.out.println("삭제하실 DB 에서 가져온 메뉴번호는" + menuRental.get().getRentalId() + " 입니다.");


        menuRentalRepository.deleteById(rentalId);
//         menuService.deleteById(menuid);
        return "메뉴가 삭제되었습니다.";
    }
    
}
