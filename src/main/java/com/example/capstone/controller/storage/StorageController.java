package com.example.capstone.controller.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.CampingArea;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.domain.storage.*;
import com.example.capstone.dto.storage.*;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingAreaRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Storage.*;
import com.example.capstone.repository.orders.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @PostMapping("/postStorage")
    public Result postStorage(@RequestBody StorageData storageData) {
        Storage storage = storageData.getStorage();
        Box box = storageData.getBox();

        Optional<Storage> storageChk = storageRepository.findByStorageName(storage.getStorageName());
        if (!storageChk.isPresent()) {
            // 보관소 추가
            storageRepository.save(storage);

            //보관함 추가
            String storageBoxName;
            String storageBoxType;
            String storageBoxState;
            Optional<Storage> storage1 = storageRepository.findByStorageName(storage.getStorageName());
            for (int i = 0; i < box.getSmall(); i++) {
                storageBoxName = "S" + (i + 1);
                storageBoxType = "S";
                storageBoxState = "0";

                StorageBox storageBoxSmall = new StorageBox(storage1.get(), storageBoxName, storageBoxType, 33000, storageBoxState);
                storageBoxRepository.save(storageBoxSmall);

                storageBoxName = "0";
                storageBoxType = "0";
                storageBoxState = "0";
            }
            for (int i = 0; i < box.getMedium(); i++) {
                storageBoxName = "M" + (i + 1);
                storageBoxType = "M";
                storageBoxState = "0";
                StorageBox storageBoxSmall = new StorageBox(storage1.get(), storageBoxName, storageBoxType, 45000, storageBoxState);
                storageBoxRepository.save(storageBoxSmall);

                storageBoxName = "0";
                storageBoxType = "0";
                storageBoxState = "0";
            }
            for (int i = 0; i < box.getLarge(); i++) {
                storageBoxName = "L" + (i + 1);
                storageBoxType = "L";
                storageBoxState = "0";
                StorageBox storageBoxSmall = new StorageBox(storage1.get(), storageBoxName, storageBoxType, 60000, storageBoxState);
                storageBoxRepository.save(storageBoxSmall);

                storageBoxName = "0";
                storageBoxType = "0";
                storageBoxState = "0";
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
        StorageManager storageManager = new StorageManager(member.get(), storage.get());
        storageManagerRepository.save(storageManager);

        return new Result("ok");
    }

    //   보관소 매니저 체크
    @GetMapping("/checkManager/{memberId}")
    public Result checkManager(@PathVariable String memberId) throws NoSuchElementException {
        try {

            Optional<Member> member = memberRepository.findByMID(memberId);
            System.out.println(member.get().getMID());
            Optional<StorageManager> storageManager = storageManagerRepository.findByMCode(member.get());

            if (storageManager.isPresent()) {
                // 중복
                return new Result("overlap");

            } else {
                // 가능
                return new Result("ok");
            }
        } catch (NoSuchElementException n) {
            System.out.println(n);
            return new Result("no");
        }
    }

    //    보관소 리스트
    @GetMapping("/getStorage")
    public List<Storage> getStorages() {
        List<Storage> storageList = storageRepository.findAll();
        return storageList;
    }

    //    보관소 상세보기
    @GetMapping("/storageView/{storageCode}")
    public Storage getStorageDetail(@PathVariable(value = "storageCode") Long storageCode) {

        List<StorageBox> boxList = storageBoxRepository.findByStorageCodeStorageCode(storageCode);

        Optional<Storage> storage = storageRepository.findById(storageCode);

        storage.get().setStorageBoxes(boxList);
        return storage.get();
    }

    //    보관함 이름 조회
    @GetMapping("/storageBoxGet/{boxCode}")
    public String getBoxName(@PathVariable(value = "boxCode") long boxCode) {
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);
        return storageBox.get().getStorageBoxName();
    }

    //  모든  매니저조회
    @GetMapping("/getStorageManger")
    public List<StorageManager> getStorageManger() {
        List<StorageManager> managerList = storageManagerRepository.findAll();

        return managerList;
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

    //보관함 연장 결제
    @PostMapping("/renewalPay")
    public Result renewalPay(@RequestBody RenewalBox renewalBox) {
        System.out.println(renewalBox.getBoxName());
        System.out.println(renewalBox.getStorageName());
        System.out.println(renewalBox.getUseBoxCode());

        Optional<Member> user = memberRepository.findByMID(renewalBox.getUserId());
        Optional<Storage> storage = storageRepository.findByStorageName(renewalBox.getStorageName());
        System.out.println(storage.get().getStorageCode());
        Optional<StorageBox> storageBox = storageBoxRepository.findByStorageCodeAndStorageBoxName(storage.get().getStorageCode(), renewalBox.getBoxName());
        Optional<UseStorageBox> beforeUseStorageBox = useStorageBoxRepository.findById(renewalBox.getUseBoxCode());
        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findByUseStorageBoxCode(beforeUseStorageBox.get());
        for (int i = 0; i < memberEquipmentList.size(); i++) {
            memberEquipmentList.get(i).setUseStorageBoxCode(null);
        }
        LocalDateTime start = renewalBox.getStartTime();
        LocalDateTime end = renewalBox.getEndTime();

        Orders orderList = new Orders(user.get());
        ordersRepository.save(orderList);

        beforeUseStorageBox.get().setUseStorageState("1");
        useStorageBoxRepository.save(beforeUseStorageBox.get());

        UseStorageBox useStorageBox = new UseStorageBox(start, end, storageBox.get(), orderList, user.get());
        useStorageBox.setUseStorageState("2");
        for (int i = 0; i < memberEquipmentList.size(); i++) {
            memberEquipmentList.get(i).setUseStorageBoxCode(useStorageBox);
        }
        useStorageBoxRepository.save(useStorageBox);

//         박스 상태 변화
        storageBox.get().setStorageBoxState("1");
//         결제된 박스 업데이트
        storageBoxRepository.save(storageBox.get());

        return new Result("ok");
    }

    //보관함 계약 해지
    @PostMapping("/closeBox")
    public Result closeBox(@RequestBody RenewalBox renewalBox) {
        Optional<Storage> storage = storageRepository.findByStorageName(renewalBox.getStorageName());
        Optional<StorageBox> storageBox = storageBoxRepository.findByStorageCodeAndStorageBoxName(storage.get().getStorageCode(), renewalBox.getBoxName());

        storageBox.get().setStorageBoxState("0");
        storageBoxRepository.save(storageBox.get());

        return new Result("ok");
    }

    // 보관함 결제
    @PostMapping("/payBox")
    public Result payStorageBox(@RequestBody payStorageBox payStorageBox) {
        Optional<Member> user = memberRepository.findByMID(payStorageBox.getUserId());
        Optional<StorageBox> storageBox = storageBoxRepository.findById(payStorageBox.getStorageBoxCode());

        LocalDateTime start = payStorageBox.getUseStorageStartTime();

        LocalDateTime end = payStorageBox.getUseStorageEndTime();

        Orders orders = new Orders(user.get(), payStorageBox.getPrice());
        ordersRepository.save(orders);

        UseStorageBox useStorageBox = new UseStorageBox(start, end, storageBox.get(), orders);
        useStorageBox.setMCode(user.get());
        useStorageBoxRepository.save(useStorageBox);

        if (payStorageBox.getItem().size() > 0) {
            System.out.println(payStorageBox.getItem().get(0));
            for (int i = 0; i < payStorageBox.getItem().size(); i++) {
                Optional<MemberEquipment> memberEquipment = memberEquipmentRepository.findById(payStorageBox.getItem().get(i));
                memberEquipment.get().setUseStorageBoxCode(useStorageBox);
                memberEquipmentRepository.save(memberEquipment.get());
            }
        } else {
            System.out.println("없다");
        }
        // 박스 상태 변화
        storageBox.get().setStorageBoxState("1");
        // 결제된 박스 업데이트
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
    public Object[] checkMember(@PathVariable("memberId") String memberId) throws NoSuchElementException {
        System.out.println("멤버아이디는" + memberId);
        try {
            Object[] useStorageBoxes = storageRepository.findByMember(memberId);

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

    //    매니저가 관리하고 있는 보관소의 각 보관함 찾기
    @GetMapping("getBox/{storageBoxCode}")
    public Object[] getBox(@PathVariable(value = "storageBoxCode") long boxCode) {
        System.out.println(boxCode);
        Optional<StorageBox> box = storageBoxRepository.findById(boxCode);
        System.out.println(box.get().getStorageBoxCode());

        Object[] object = storageBoxRepository.findByBoxInformation(box.get().getStorageBoxCode());
        for (int i = 0; i < object.length; i++) {
            System.out.println(object[i].toString());
        }
        if (object.length < 1) {
            return new Optional[]{storageBoxRepository.findById(box.get().getStorageBoxCode())};
        }

        return object;
    }

    //보관소 매니저 리스트 조회
    @GetMapping("stGetManager/{storageCode}")
    private List<StorageManager> getPickManager(@PathVariable(value = "storageCode") long storageCode) {
        Optional<Storage> storage = storageRepository.findById(storageCode);
        List<StorageManager> storageManagerList = storageManagerRepository.findByStorageCode(storage.get());

        return storageManagerList;
    }

    // 보관함 장소 이동
    @PostMapping("roundMoveBox")
    private Result roundmovePay(@RequestBody RoundMove roundMove) {
        System.out.println(roundMove.getUseBoxCode());
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(roundMove.getUseBoxCode());
        System.out.println(useStorageBox.get().getUseStorageState());
        if (useStorageBox.get().getUseStorageState().equals("2")) {
            useStorageBox.get().setUseStorageState("4");
            useStorageBoxRepository.save(useStorageBox.get());

            Optional<Member> member = memberRepository.findByMID(roundMove.getUserId());

            Orders orderList = new Orders(member.get());
            ordersRepository.save(orderList);

            return new Result("ok");
        } else if (useStorageBox.get().getUseStorageState() == "1") {
            return new Result("umm");
        } else {
            return new Result("no");
        }

    }

    // 보관함 가격 조회
    @GetMapping("boxPrice/{boxCode}")
    private int getBoxPrice(@PathVariable(value = "boxCode")long box){
        Optional<StorageBox> storageBox= storageBoxRepository.findById(box);
        return storageBox.get().getStorageBoxPrice();
    }

    // 보관함 보관소 이동
    @PostMapping("boxToBoxPay")
    private Result boxToBoxPay(@RequestBody StorageMove move) {
        // 결제 시간
        LocalDateTime orderTime = LocalDateTime.now();

        // 사용자 조회
        Optional<Member> member = memberRepository.findByMID(move.getUserId());

        // 이동할 사용중인 보관함
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(move.getUse());

        // 결제,
        Orders orders = new Orders(member.get());
        orders.setPaymentDate(orderTime);
        orders.setMCode(member.get());
        ordersRepository.save(orders);

        // 보관함 상태 코드 변경경
        Optional<StorageBox> beforeBox = storageBoxRepository.findById(move.getBefore());
        beforeBox.get().setStorageBoxState("3"); // 장비 이동 신청옴 출발지
        storageBoxRepository.save(beforeBox.get());

        // 이동될 보관함 상태 코드 변경
        Optional<StorageBox> afterBox = storageBoxRepository.findById(move.getAfter());
        afterBox.get().setStorageBoxState("4");  // 장비 이동 신청 옴 도착지
        storageBoxRepository.save(afterBox.get());

        // 이동될 사용 보관함 상태코드(상태 + 이전 사용보관함코드)
        UseStorageBox newUseStorageBox = new UseStorageBox(orderTime,
                useStorageBox.get().getUseStorageEndTime(),
                "4" + useStorageBox.get().getUseStorageBoxCode(),
                afterBox.get(), orders);
        newUseStorageBox.setMCode(member.get());
        useStorageBoxRepository.save(newUseStorageBox);

        // 이동할 사용중인 보관함 상태코드에서 이동될 사용중 보관함 코드 추가
        // 이동될 사용 보관함 찾기
        String state = "4" + useStorageBox.get().getUseStorageBoxCode();
        Optional<UseStorageBox> newUSB = useStorageBoxRepository.findByUseStorageState(state);
        useStorageBox.get().setUseStorageState("3" + newUSB.get().getUseStorageBoxCode());
        useStorageBoxRepository.save(useStorageBox.get());

        return new Result("ok");
    }

    // 보관소 에서 보관소로 이동시 어디로 가는 지에 대한 정보
    @GetMapping("moveBoxInfo/{userBoxCode}")
    private Object[] moveBoxInfo(@PathVariable(value = "userBoxCode") long userBoxCode) {
        Object[] useStorageBox = useStorageBoxRepository.findByInFo(userBoxCode);
        return useStorageBox;
    }

    //  보관소 보관소 이동 시작 -> 이동중으로 변경
    @GetMapping("moveStateUpdate/{beforeBox}/{afterBox}")
    private Result moveStateUpdate(@PathVariable(value = "beforeBox")long beforeBox,@PathVariable(value = "afterBox")long afterBox ){
        Optional<UseStorageBox> beforeUSBox = useStorageBoxRepository.findById(beforeBox);
        Optional<UseStorageBox> afterUSBox = useStorageBoxRepository.findById(afterBox);
        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findByUseStorageBoxCode(beforeUSBox.get());

        for (int i = 0; i < memberEquipmentList.size(); i++) {
            memberEquipmentList.get(i).setUseStorageBoxCode(afterUSBox.get());
            memberEquipmentRepository.save(memberEquipmentList.get(i));
        }

        LocalDateTime nowTime = LocalDateTime.now();

        UseStorageBox before = beforeUSBox.get();
        before.setUseStorageState("1");
        before.setUseStorageEndTime(nowTime);
        afterUSBox.get().setUseStorageState("5" + beforeUSBox.get().getUseStorageBoxCode());

        StorageBox storageBox = beforeUSBox.get().getStorageBoxCode();
        storageBox.setStorageBoxState("0");
        storageBoxRepository.save(storageBox);
        useStorageBoxRepository.save(before);
        useStorageBoxRepository.save(afterUSBox.get());

        return new Result("ok");
    }

    // 보관소 이동 도착
    @GetMapping("endmoveUpdate/{afterBox}")
    private Result endmoveUpdate(@PathVariable(value = "afterBox")long afterBox ){
        Optional<UseStorageBox> afterUSBox = useStorageBoxRepository.findById(afterBox);

        afterUSBox.get().setUseStorageState("2");

        StorageBox storageBox = afterUSBox.get().getStorageBoxCode();
        storageBox.setStorageBoxState("2");

        storageBoxRepository.save(storageBox);
        useStorageBoxRepository.save(afterUSBox.get());

        return new Result("ok");
    }

    // 내장비 조회
    @GetMapping("myItem/{userId}")
    private List<MemberEquipment> myItem(@PathVariable(value = "userId") String userId) {
        Optional<Member> member = memberRepository.findByMID(userId);
        long mCode = member.get().getMCode();
        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findByMemEquipment(mCode);

        return memberEquipmentList;
    }

    //사용자 사용하는 보관함의 장비 조회
    @GetMapping("getBoxInItem/{useBoxCode}")
    private List<MemberEquipment> getMyItem(@PathVariable(value = "useBoxCode") Long useBoxCode) {
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(useBoxCode);
        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findByUseStorageBoxCode(useStorageBox.get());

        return memberEquipmentList;
    }

    //사용자주소 조회
    @GetMapping("myAddress/{mid}")
    private Member getMemberAddress(@PathVariable(value = "mid") String mid) {
        Optional<Member> member = memberRepository.findByMID(mid);

        return member.get();
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
