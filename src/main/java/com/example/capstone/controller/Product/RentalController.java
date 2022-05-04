package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
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

}
