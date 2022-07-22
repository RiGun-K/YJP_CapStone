package com.example.capstone.controller.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.CampingArea;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.domain.storage.*;
import com.example.capstone.dto.storage.*;
import com.example.capstone.dto.storage.Box;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingAreaRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Storage.*;
import com.example.capstone.repository.orders.OrdersRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api")
public class StorageController {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private StorageManagerRepository storageManagerRepository;

    @Autowired
    private StorageBoxRepository storageBoxRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UseStorageBoxRepository useStorageBoxRepository;

    @Autowired
    KindRepository kindRepository;

    @Autowired
    MemberEquipmentRepository memberEquipmentRepository;

    @Autowired
    CampingAreaRepository campingAreaRepository;

    @Autowired
    BoxItemRepository boxItemRepository;

    @PostMapping("/postStorage")
    public Result postStorage(@RequestParam(value = "file", required = false) MultipartFile uploadFile, StorageData storageData)throws IllegalStateException, IOException {
//        Storage storage = storageData.getStorage();
//        Box box = storageData.getBox();
        Storage storage = new Storage();
        storage.setStorageName(storageData.getStorageName());
        storage.setStorageZipcode(storageData.getStorageZipcode());
        storage.setStorageAddress(storageData.getStorageAddress());
        storage.setStorageDetailAddress(storageData.getStorageDetailAddress());
        storage.setStorageTel(storageData.getStorageTel());
        storage.setLatitude(storageData.getLatitude());
        storage.setLongitude(storageData.getLongitude());
        storage.setStorageState("1");
        Box box = new Box();
        box.setSmall(storageData.getSmall());
        box.setMedium(storageData.getMedium());
        box.setLarge(storageData.getLarge());

        Optional<Storage> storageChk = storageRepository.findByStorageName(storage.getStorageName());

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
            storageData.setFilename(filename);
            storageData.setFilePath("\\src\\frontend\\src\\assets\\"+ filename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        storage.setFilename(storageData.getFilename());
        storage.setFilePath(storageData.getFilePath());

        if (!storageChk.isPresent()) {
            // 보관소 추가
            storageRepository.save(storage);

            //보관함 추가
            Optional<Storage> storage1 = storageRepository.findByStorageName(storage.getStorageName());
            for (int i = 0; i < box.getSmall(); i++) {
                StorageBox storageBoxSmall = new StorageBox();
                storageBoxSmall.setStorageCode(storage1.get());
                storageBoxSmall.setStorageBoxPrice(33000);
                storageBoxSmall.setStorageBoxName("S" + (i + 1));
                storageBoxSmall.setStorageBoxType("S");
                storageBoxSmall.setStorageBoxState("x");
                storageBoxRepository.save(storageBoxSmall);
            }
            for (int i = 0; i < box.getMedium(); i++) {
                StorageBox storageBoxMedium = new StorageBox();
                storageBoxMedium.setStorageCode(storage1.get());
                storageBoxMedium.setStorageBoxPrice(45000);
                storageBoxMedium.setStorageBoxName("M" + (i + 1));
                storageBoxMedium.setStorageBoxType("M");
                storageBoxMedium.setStorageBoxState("x");
                storageBoxRepository.save(storageBoxMedium);
            }
            for (int i = 0; i < box.getLarge(); i++) {
                StorageBox storageBoxLarge = new StorageBox();
                storageBoxLarge.setStorageCode(storage1.get());
                storageBoxLarge.setStorageBoxPrice(60000);
                storageBoxLarge.setStorageBoxName("L" + (i + 1));
                storageBoxLarge.setStorageBoxType("L");
                storageBoxLarge.setStorageBoxState("x");
                storageBoxRepository.save(storageBoxLarge);
            }

            return new Result("ok");
        } else {
            return new Result("no");
        }

    }

    //   보관소 매니저 생성
    @PostMapping("/postManager")
    public Result postManager(@RequestBody Manager manager) {
        System.out.println(manager.getStorage());
        Optional<Storage> storage = storageRepository.findById(manager.getStorage());
        Optional<Member> member = memberRepository.findByMID(manager.getMember());
        StorageManager storageManager = new StorageManager();
        storageManager.setMCode(member.get());
        storageManager.setStorageCode(storage.get());
        storageManagerRepository.save(storageManager);

        return new Result("ok");
    }

    //   보관소 매니저 체크
    @GetMapping("/checkManager/{memberId}")
    public Result checkManager(@PathVariable String memberId) {
            Optional<Member> member = memberRepository.findByMID(memberId);
            System.out.println(member.get().getMID());
            Optional<StorageManager> storageManager = storageManagerRepository.findByMCode(member.get());

            if (!storageManager.isEmpty()) {
                // 중복
                return new Result("overlap");
            } else {
                // 가능

                if(member.get().getMSC().equals("5")){
                    return new Result("ok");
                }else{
                    return new Result("no");
                }
            }
    }

    //관리자 보관소 리스트
    @GetMapping("/getStorageAdmin")
    public List<Storage> getStorageAdmin() {
        List<Storage> storageList = storageRepository.findAll();
        return storageList;
    }

    //    보관소 리스트
    @GetMapping("/getStorage")
    public List<Storage> getStorages() {
        List<Storage> storageList = storageRepository.findByNotState();
        return storageList;
    }

//    보관함 코드로 보관소이름 보관함이름 조죄
    @GetMapping("getNamesToBoxCode/{boxCode}")
    public Object[] getNamesToBoxCode(@PathVariable(value = "boxCode")long boxCode){
        Object[] storage = storageBoxRepository.findByNamesToBoxCode(boxCode);
        return storage;
    }

//    보관함코드로 보관소 정보 찾기
    @GetMapping("/getStorageToBox/{boxCode}")
    public Storage getStorageToBox(@PathVariable(value = "boxCode")long boxCode){
        Optional<Storage> storage = storageRepository.findByStorageBoxCode(boxCode);
        return storage.get();
    }

    //    보관소 상세보기
    @GetMapping("/storageView/{storageCode}")
    public Storage getStorageDetail(@PathVariable(value = "storageCode") Long storageCode) {

        List<StorageBox> boxList = storageBoxRepository.findByStorageCodeStorageCode(storageCode);

        Optional<Storage> storage = storageRepository.findById(storageCode);

        storage.get().setStorageBoxes(boxList);
        return storage.get();
    }

    //    보관함 코드 조회
    @GetMapping("/storageBoxGet/{storageName}/{boxName}")
    public long getBoxName(@PathVariable(value = "storageName") String storageName,@PathVariable(value = "boxName") String boxName) {
        Optional<StorageBox> storageBox = storageBoxRepository.findByStorageNameAndStorageBoxName(storageName,boxName);
        return storageBox.get().getStorageBoxCode();
    }


    //  모든 매니저조회
    @GetMapping("/getStorageManger")
    public List<StorageManager> getStorageManger() {
        List<StorageManager> managerList = storageManagerRepository.findAll();
        for (int i = 0; i < managerList.size(); i++) {
            System.out.println("매니저 이름" + managerList.get(i).getMCode().getMname());
            System.out.println("보관소이름"+ managerList.get(i).getStorageCode().getStorageName());
        }

        return managerList;
    }

    // 매니저 삭제
    @GetMapping("/deleteManager/{code}")
    public Result deleteManager(@PathVariable(value = "code")long code){
        Optional<StorageManager> storageManager = storageManagerRepository.findById(code);
        storageManagerRepository.delete(storageManager.get());
        return new Result("ok");
    }

    //    보관함 매니저 조회
    @GetMapping("/getManager/{storageCode}")
    public List<StorageManager> getManager(@PathVariable(value = "storageCode") long storageCode) {
        Optional<Storage> storage = storageRepository.findById(storageCode);
        List<StorageManager> managerList = storageManagerRepository.findByStorageCode(storage.get());
        return managerList;
    }

    // 각 보관함별 사용일 찾기
    @GetMapping("/findUseTime/{boxCode}")
    public List<UseStorageBox> findUseTime(@PathVariable(value = "boxCode") long storageBoxCode) {
        Optional<StorageBox> box = storageBoxRepository.findById(storageBoxCode);
        List<UseStorageBox> useBox = useStorageBoxRepository.findByStorageBoxCode(box.get());
        return useBox;
    }

    // 보관소 관리자가 보관함이 비어졋음을 알리는거
    @GetMapping("/clearBox/{boxCode}")
    public Result clearBox(@PathVariable(value = "boxCode")long boxCode){
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);
        if (storageBox.isEmpty()){
            return new Result("no");
        }
        storageBox.get().setStorageBoxState("0");
        storageBoxRepository.save(storageBox.get());
        return new Result("ok");
    }


    //보관함 보관 확인
    @PutMapping("/boxStateUpdate/{updateState}")
    public Result updateBoxState(@PathVariable(value = "updateState") long code) {
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(code);
        // 사용중인 보관함 상태코드 2(구독중) 변경
        useStorageBox.get().setUseStorageState("2");

        // 보관함 상태코드 2(보관중) 변경
        useStorageBox.get().getStorageBoxCode().setStorageBoxState("2");

        //update
        useStorageBoxRepository.save(useStorageBox.get());
        return new Result("ok");
    }

    //    보관함 사용중인 사용자 조회
    @GetMapping("checkMember/{memberId}")
    public List<Map<String, Object>> checkMember(@PathVariable("memberId") String memberId) throws NoSuchElementException {
        try {
//            Object[] useStorageBoxes = storageRepository.findByMember(memberId);
            List<Map<String, Object>> useStorageBoxes = storageRepository.findByMember(memberId);
//            System.out.println(useStorageBoxes.size());
//            for (int i = 0; i < useStorageBoxes.size(); i++) {
//                System.out.println(useStorageBoxes.get(i).size());
//            }
            return useStorageBoxes;

        } catch (NoSuchElementException n) {
            System.out.println(n);
            System.out.println("안돼");
            return null;
        }
    }

    //   매니저 확인 할 때 사용
    @GetMapping("managerCheck/{managerId}")
    public Result getManagerCheck(@PathVariable(value = "managerId") String managerId) throws NoSuchElementException {
        try {
            Optional<Member> member = memberRepository.findByMID(managerId);
            Optional<StorageManager> storageManager = storageManagerRepository.findByMCode(member.get());

            if (storageManager.isPresent()) {
                return new Result("ok");
            } else {
                return new Result("no");
            }
        } catch (NoSuchElementException n) {
            System.out.println(n);
            return new Result("no");
        }
    }

    //    매니저가 관리하고 있는 보관소 불러오기
    @GetMapping("getManagerStorage/{managerId}")
    public Storage getManagerStorage(@PathVariable(value = "managerId") String managerId) {
        Optional<Member> member = memberRepository.findByMID(managerId);
        Optional<StorageManager> storageManager = storageManagerRepository.findByMCode(member.get());
        Optional<Storage> storageBoxList = storageRepository.findById(storageManager.get().getStorageCode().getStorageCode());

        return storageBoxList.get();
    }

    //    매니저가 관리하고 있는 보관소의 각 보관함 찾기(사용 중)
    @GetMapping("getBox/{storageBoxCode}")
    public Object[] getBox(@PathVariable(value = "storageBoxCode") long boxCode) {
        Optional<StorageBox> box = storageBoxRepository.findById(boxCode);

        Object[] object = storageBoxRepository.findByBoxInformation(box.get().getStorageBoxCode());
        if (object.length < 1) {
            return new Optional[]{storageBoxRepository.findById(box.get().getStorageBoxCode())};
        }

        return object;
    }

    //    구독 연장시 연장된 정보로 조회
    @GetMapping("moreUseBox/{boxCode}")
    public Object[] moreUseBox(@PathVariable(value = "boxCode")long boxCode){
        Object[] infoData = storageBoxRepository.findByBoxInformationOfState(boxCode);
        return infoData;
    }

    //  매니저가 관리하고 있는 보관소의 각 보관함 찾기(사용 안함)
    @GetMapping("notUsingBoxInfo/{boxCode}")
    public StorageBox notUsingBoxInfo(@PathVariable(value = "boxCode")long boxCode){
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);

        return storageBox.get();
    }

//    매니저가 해지하는 보관소 배송시작 시 보관함 상태코드 0으로 변경
    @GetMapping("endBoxState/{useCode}")
    Result endBoxState(@PathVariable(value = "useCode")long useCode){
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(useCode);
        List<BoxItem> boxItemList = boxItemRepository.findByUseStorageBoxCode(useStorageBox.get());
        Optional<StorageBox> storageBox = storageBoxRepository.findById(useStorageBox.get().getStorageBoxCode().getStorageBoxCode());
        StorageBox storageBox1 = storageBox.get();
        storageBox1.setStorageBoxState("2");
        storageBoxRepository.save(storageBox1);
        useStorageBox.get().setUseStorageState("1");
        useStorageBoxRepository.save(useStorageBox.get());

        for (int i = 0; i < boxItemList.size(); i++) {
            Optional<MemberEquipment> memberEquipment = memberEquipmentRepository.findById(boxItemList.get(i).getMemEquipmentCode().getMemEquipmentCode());
            memberEquipment.get().setMemEquipmentState("0");
            memberEquipmentRepository.save(memberEquipment.get());
            boxItemRepository.delete(boxItemList.get(i));
        }
        return new Result("ok");
    }


    //보관소 정보 업데이트
    @PostMapping("updateStorage")
    public Result updateStorage(@RequestBody Storage update){
        Optional<Storage> storage = storageRepository.findById(update.getStorageCode());
        if (storage.isEmpty()){
            return new Result("no");
        }
        storage.get().setStorageName(update.getStorageName());
        storage.get().setStorageZipcode(update.getStorageZipcode());
        storage.get().setStorageAddress(update.getStorageAddress());
        storage.get().setStorageDetailAddress(update.getStorageDetailAddress());
        storage.get().setStorageTel(update.getStorageTel());

        storageRepository.save(storage.get());
        return new Result("ok");
    }

    //보관소 개업
    @GetMapping("openStorage/{storageCode}")
    public Result openStorage(@PathVariable(value = "storageCode")long storageCode){
        Optional<Storage> storage = storageRepository.findById(storageCode);

        List<StorageBox> storageBoxes = storageBoxRepository.findByStorageCodeStorageCode(storageCode);

        for (int i = 0; i < storageBoxes.size(); i++) {
            if (storageBoxes.get(i).getStorageBoxState() == "x"){
                return new Result("no");
            }
        }

        storage.get().setStorageState("0");
        storageRepository.save(storage.get());
        return new Result("ok");
    }

    //보관소 폐업
    @GetMapping("closeStorage/{storageCode}")
    public Result closeStorage(@PathVariable(value = "storageCode")long storageCode){
        Optional<Storage> storage = storageRepository.findById(storageCode);


        storage.get().setStorageState("1");
        storageRepository.save(storage.get());
        return new Result("ok");
    }

    //보관함 활성화
    @GetMapping("onBox/{boxCode}")
    public Result onBox(@PathVariable(value = "boxCode")long boxCode){
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);
        storageBox.get().setStorageBoxState("0");

        storageBoxRepository.save(storageBox.get());

        return new Result("ok");
    }

    //보관함 비활성화
    @GetMapping("offBox/{boxCode}")
    public Result offBox(@PathVariable(value = "boxCode")long boxCode){
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);
        List<UseStorageBox> useStorageBoxes = useStorageBoxRepository.findByStorageBoxCode(storageBox.get());
        for (int i = 0; i < useStorageBoxes.size(); i++) {
            if (!useStorageBoxes.get(i).getUseStorageState().equals("1")){
                return new Result("no");
            }
        }

        storageBox.get().setStorageBoxState("x");

        storageBoxRepository.save(storageBox.get());

        return new Result("ok");
    }

    //보관소 매니저 리스트 조회
    @GetMapping("stGetManager/{storageCode}")
    private List<StorageManager> getPickManager(@PathVariable(value = "storageCode") long storageCode) {
        Optional<Storage> storage = storageRepository.findById(storageCode);
        List<StorageManager> storageManagerList = storageManagerRepository.findByStorageCode(storage.get());

        return storageManagerList;
    }


//    보관함 사용 연장을 위한 시간조회
    @GetMapping("useTime/{useCode}")
    public UseStorageBox getUseT(@PathVariable(value = "useCode")long useCode){
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(useCode);
        return useStorageBox.get();
    }

    // 보관함 가격 조회
    @GetMapping("boxPrice/{boxCode}")
    private int getBoxPrice(@PathVariable(value = "boxCode")long box){
        Optional<StorageBox> storageBox= storageBoxRepository.findById(box);
        return storageBox.get().getStorageBoxPrice();
    }

//    매니저가 사용자 장비 집으로 배송
    @GetMapping("endMoveToHome/{useCode}")
    private Result endMoveToHome(@PathVariable(value = "useCode")long useCode){
        Optional<UseStorageBox> useBox = useStorageBoxRepository.findById(useCode);
        UseStorageBox useStorageBox = useBox.get();
        List<BoxItem> boxItemList = boxItemRepository.findByUseStorageBoxCode(useStorageBox);
        for (int i = 0; i < boxItemList.size(); i++) {
            BoxItem boxItem = boxItemList.get(i);
            if (boxItem.getBoxItemState().substring(0,1).equals("4")){
                int count =  Integer.parseInt(boxItem.getBoxItemState().substring(1));
                if (boxItem.getBoxItemCount() == count){
                    boxItemRepository.delete(boxItem);
                }else{
                    boxItem.setBoxItemCount(boxItem.getBoxItemCount()-count);
                    boxItemRepository.save(boxItem);
                }
            }else if(boxItem.getBoxItemState().substring(0,1).equals("5")){
                int count =  Integer.parseInt(boxItem.getBoxItemState().substring(1));
                if (boxItem.getBoxItemCount() == count){
                    boxItemRepository.delete(boxItem);
                }else{
                    boxItem.setBoxItemCount(boxItem.getBoxItemCount()-count);
                    boxItemRepository.save(boxItem);
                }
            }else{
                return new Result("no");
            }
        }
        useStorageBox.setUseStorageState("2");
        useStorageBoxRepository.save(useStorageBox);
        StorageBox storageBox = useStorageBox.getStorageBoxCode();
        storageBox.setStorageBoxState("2");
        storageBoxRepository.save(storageBox);

        return new Result("ok");
    }

//   매니저가 관리하는 보관함 종료되면 보관함 상태코드 0으로 변경
    @GetMapping("updateBoxStateChk/{storageCode}")
    private Result updateBoxStateChk(@PathVariable(value = "storageCode")long code){
        List<StorageBox> storageBoxList = storageBoxRepository.findByStorageCodeStorageCode(code);
        for (int i = 0; i < storageBoxList.size(); i++) {
            List<UseStorageBox> useStorageBoxList = useStorageBoxRepository.findByStorageBoxCode(storageBoxList.get(i));
            for (int j = 0; j < useStorageBoxList.size(); j++) {
                LocalDate a = LocalDate.now();
                if (!useStorageBoxList.get(j).getUseStorageEndTime().isAfter(a)){
                    storageBoxList.get(i).setStorageBoxState("0");
                    storageBoxRepository.save(storageBoxList.get(i));
                }
            }
        }

        return new Result("ok");
    }


    ////////////////////////// 지역 조회  //////////////////////////

    @GetMapping("aRound")
    private List<CampingArea> getRound() {

        List<CampingArea> campingAreaList = campingAreaRepository.findByParentAreaList();

        return campingAreaList;
    }

    @GetMapping("smallRound/{parentId}")
    private List<CampingArea> getSmallRound(@PathVariable(value = "parentId") int parentId) {

        List<CampingArea> campingAreaList = campingAreaRepository.findCampingAreaByParentcampingarea(parentId);

        return campingAreaList;
    }

    @GetMapping("roundPick/{bigName}/{smallName}")
    private List<Storage> getStoragePick(@PathVariable(value = "bigName") int bigName, @PathVariable(value = "smallName") int smallName) throws IllegalArgumentException {
        try {
            Optional<CampingArea> cbigName = campingAreaRepository.findById(bigName);
            if (smallName != 0) {
                Optional<CampingArea> csmallName = campingAreaRepository.findById(smallName);
                String sName = csmallName.get().getAreaName();
                String bName = cbigName.get().getAreaName();
                System.out.println(bName + " " + sName);
                String search = bName.substring(0, 2) + " " + sName;
                System.out.println(search);
                List<Storage> storageList = storageRepository.findByStorageAddress(search);

                return storageList;
            } else {
                String bName = cbigName.get().getAreaName();
                System.out.println(bName.substring(0, 2));
                String search = bName.substring(0, 2);
                List<Storage> storageList = storageRepository.findByStorageAddress(search);

                return storageList;
            }
        } catch (IllegalArgumentException i) {
            System.out.println(i);
            System.out.println("에러");
            return null;
        }
    }
}
