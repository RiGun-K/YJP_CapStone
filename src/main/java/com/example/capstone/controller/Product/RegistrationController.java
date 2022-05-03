package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import com.example.capstone.dto.Product.MenuBuyDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
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
@AllArgsConstructor
@RequestMapping("/api")
public class RegistrationController {


    MemberRepository memberRepository;

    MenuBuyRepository menuBuyRepository;

    KindRepository kindRepository;


    /* 구매상품 등록 페이지 */
    @PostMapping("/Buy_Signup")
    public MenuBuy addMenu(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuyDTO MenuBuyDTO) throws IllegalStateException, IOException {
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
    public MenuRental addMenu(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuyDTO MenuBuyDTO) throws IllegalStateException, IOException {
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

        return MenuRental;
    }

    /* 캠핑장예약 등록 페이지 */
    @PostMapping("/Camping_Signup")
    public Camping addMenu(@RequestParam(value = "file", required = false) MultipartFile uploadFile, MenuBuyDTO MenuBuyDTO) throws IllegalStateException, IOException {
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
}
