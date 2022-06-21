package com.example.capstone.controller.Product;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.domain.Product.CampingDetail;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.Product.MenuRental;
import com.example.capstone.dto.Product.MenuDTO;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.*;
import com.example.capstone.service.ProductService;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.util.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
//@CrossOrigin("*")
//@RequiredArgsConstructor
//@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
//@AllArgsConstructor  // @Autowired 대신사용 , But 쓰면 DB 저장안됨 ( Null )
@RequestMapping("/api")
public class ProductController {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MenuBuyRepository menuBuyRepository;

    @Autowired
    MenuRentalRepository menuRentalRepository;

    @Autowired
    CampingRepository campingRepository;

    @Autowired
    CampingDetailRepository campingDetailRepository;

    @Autowired
    KindRepository kindRepository;

    @Autowired
    ProductService productService;


    /* 판매자 정보 */
    @GetMapping("getMymember/{user}")
    public Optional<Member> getAllmember(@PathVariable("user") String user){
        Optional<Member> members = memberRepository.findByMID(user);

        return members;
    }


    /* 구매상품 리스트 */
    @GetMapping("/product_BuyList")
    public List<MenuBuy> menuBuysList() {
        List<MenuBuy> menus = menuBuyRepository.findByAllBuyId();
        System.out.println(menus);
        return menus;
    }

    /* 렌탈상품 리스트 */
    @GetMapping("/product_RentalList")
    public List<MenuRental> menuRentalsList() {
        List<MenuRental> menus = menuRentalRepository.findAll();
        System.out.println(menus);
        return menus;
    }

    /* 캠핑상품 리스트 */
    @GetMapping("/product_CampingList")
    public List<Camping> menuCampingsList() {
        List<Camping> menus = campingRepository.findAll();
        System.out.println(menus);
        return menus;
    }


    /* 구매상품 결제전 상세 페이지 */
    @GetMapping("/product_detailB/{buyId}")
    public Optional<MenuBuy> getProduct_DetailB(@PathVariable("buyId") int buyId) {
        Optional<MenuBuy> menuDetailList = menuBuyRepository.findById(buyId);
        return menuDetailList;
    }

    /* 렌탈상품 결제전 상세 페이지 */
    @GetMapping("/product_detailRR/{rentalId}")
    public Optional<MenuRental> getProduct_DetailRR(@PathVariable("rentalId") int rentalId) {
        Optional<MenuRental> rentalDetailList = menuRentalRepository.findById(rentalId);
        return rentalDetailList;
    }

    /* 캠핑장 결제전 상세 페이지 */
    @GetMapping("/product_detailC/{campingId}")
    public Optional<Camping> getProduct_DetailC(@PathVariable("campingId") int campingId) {
        Optional<Camping> campingDetailList = campingRepository.findById(campingId);
        return campingDetailList;
    }

    /* 캠핑장 결제전 상세 페이지에서 객실조회 후 예약 및 결제 페이지 */
    @GetMapping("/product_detailR/{detailId}")
    public Optional<CampingDetail> getProduct_DetailR(@PathVariable("detailId") int detailId) {
        Optional<CampingDetail> campingDetailList = campingDetailRepository.findById(detailId);
        return campingDetailList;
    }

    /* 캠핑장 결제전 상세 페이지에서 객실조회 후 예약 및 결제 페이지 내 현재 사용자 정보 */
    @GetMapping("/product_detailU/{MCode}")
    public Optional<Member> getProduct_DetailU(@PathVariable("MCode") long MCode) {
        Optional<Member> campingDetailList = memberRepository.findById(MCode);
        return campingDetailList;
    }

    /* 상품 결제 페이지 전 해당 상품이미지 불러오기 */
    @GetMapping(value = "/product_detail_images/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] imagesSearch(@PathVariable("filename") String filename, HttpServletResponse httpServletResponse) throws IOException {

        System.out.println(filename);
        System.out.println("-------------------1----------------");

        String requestPath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets\\" + filename;


        InputStream imageStream = new FileInputStream(requestPath);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }

    /* 캠핑장 종류별 조회하기 ( 캠핑 ) */
    @GetMapping("/product_detail_camping/{infoterId}")
    public List<Camping> campings(@PathVariable("infoterId") int infoterId) {
        List<Camping> campings = campingRepository.findByinfoterId(infoterId);
        System.out.println(campings);
        return campings;
    }

    /* 캠핑장 지역별 조회하기 ( 캠핑 ) */
    @GetMapping("/product_detail_campingArea/{areaId}")
    public List<Camping> campingAreas(@PathVariable("areaId") int areaId) {
        List<Camping> campings = campingRepository.findByareaId(areaId);
        System.out.println(campings);
        return campings;
    }

    /* 캠핑장 지역별 조회하기 ( 캠핑 ) */
    @GetMapping("/product_detail_campingDetailArea/{areaId}")
    public List<Camping> campingAreaDetails(@PathVariable("areaId") int areaId) {
        System.out.println(areaId);
        List<Camping> campings = campingRepository.findByareaDetailId(areaId);
        System.out.println(campings);
        return campings;
    }

    /* 캠핑장 종류별 조회하기 ( 조회순 ) */
    @GetMapping("/product_detail_campingManyViews")
    public List<Camping> campingManyViews() {
        List<Camping> campings = campingRepository.findAllListViewDesc();
        System.out.println(campings);
        return campings;
    }

    /* 캠핑장 종류별 조회하기 ( 최신순 ) */
    @GetMapping("/product_detail_campingDesc")
    public List<Camping> campingDesc() {
        List<Camping> campings = campingRepository.findAllListDesc();
        System.out.println(campings);
        return campings;
    }


    /* 나의상품 리스트 */
//    @GetMapping("/myProduct_list/{userid}")
////    @JsonProperty("menu")
//    public List<Menu> getMenuMyList(@RequestBody long userid) {
//
////        System.out.println(menuList.get(0).getKindid().getKindname());
////        List<Menu> myList = menuRepository.findAllByMenuList("dongmin");
//        System.out.println(userid + " 분 의 상품을 조회하겠습니다.");
//        Member user = productService.findByMemberId(userid);
////        List<Member> member = memberRepository.findByMID(menuDTO.getMid());
//        List<Menu> menuMyList = menuRepository.findByMID(user);
////        List<Member> members = memberRepository.findById(member);
//
//        return menuMyList;
//    }





    /* 나의상품 수정 */
//    @PutMapping("/myProduct_Update/{menuid}")
//    public String UpdateMyProduct_Detail(ProductDetailDTO productDetailDTO) {
//        productService.savePost(productDetailDTO);
//        return "게시글이 수정되었습니다";
//    }




}
