package com.example.capstone.controller.Product;

import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.CampingDetail;
import com.example.capstone.dto.Product.CampingDetailDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
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
            campingDetailDTO.setSavedTime(LocalDate.now().toString());


        CampingDetail campingDetail = new CampingDetail(campingDetailDTO.getDetailName(), campingDetailDTO.getDetailPrice(), campingDetailDTO.getBaseNumber(), campingDetailDTO.getMaximumNumber(), campingDetailDTO.getDetailFunction(), campingDetailDTO.getSavedTime(), campingDetailDTO.getOrigFilename(), campingDetailDTO.getFilename(), campingDetailDTO.getFilePath(), camping.get());
        System.out.println(campingDetail);

        campingDetailRepository.save(campingDetail);

        return campingDetail;
    }
}
