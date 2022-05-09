package com.example.capstone.controller.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.domain.storage.*;
import com.example.capstone.dto.storage.*;
import com.example.capstone.repository.Member.MemberRepository;
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
                storageBoxType = "0";
                storageBoxState = "0";

                StorageBox storageBoxSmall = new StorageBox(storage1.get(), storageBoxName, storageBoxType, 33000, storageBoxState);
                storageBoxRepository.save(storageBoxSmall);

                storageBoxName = "0";
                storageBoxType = "0";
                storageBoxState = "0";
            }
            for (int i = 0; i < box.getMedium(); i++) {
                storageBoxName = "M" + (i + 1);
                storageBoxType = "0";
                storageBoxState = "0";
                StorageBox storageBoxSmall = new StorageBox(storage1.get(), storageBoxName, storageBoxType, 45000, storageBoxState);
                storageBoxRepository.save(storageBoxSmall);

                storageBoxName = "0";
                storageBoxType = "0";
                storageBoxState = "0";
            }
            for (int i = 0; i < box.getLarge(); i++) {
                storageBoxName = "L" + (i + 1);
                storageBoxType = "0";
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

    @GetMapping("/storageBoxGet/{boxCode}")
    public String getBoxName(@PathVariable(value = "boxCode") long boxCode) {
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);
        return storageBox.get().getStorageBoxName();
    }

    @GetMapping("/boxPrice/{boxCode}")
    public int getBoxPrice(@PathVariable(value = "boxCode") long boxCode) {
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);
        return storageBox.get().getStorageBoxPrice();
    }


    @GetMapping("/getStorageManger")
    public List<StorageManager> getStorageManger() {
        List<StorageManager> managerList = storageManagerRepository.findAll();

        return managerList;
    }

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

        Optional<Member> user = memberRepository.findByMID(renewalBox.getUserId());
        Optional<Storage> storage = storageRepository.findByStorageName(renewalBox.getStorageName());
        Optional<StorageBox> storageBox = storageBoxRepository.findByStorageCodeAndStorageBoxName(storage.get().getStorageCode(), renewalBox.getBoxName());

        LocalDateTime start = renewalBox.getStartTime();
        LocalDateTime end = renewalBox.getEndTime();

        Orders orderList = new Orders(user.get());
        ordersRepository.save(orderList);

        UseStorageBox useStorageBox = new UseStorageBox(start, end, storageBox.get(), orderList);
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
        useStorageBoxRepository.save(useStorageBox);

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

    //    로그인 없이 보관함 사용중인 사용자 조회
    @GetMapping("checkMember/{memberId}")
    public Object[] checkMember(@PathVariable("memberId") String memberId) throws NoSuchElementException {
        try {
            Object[] useStorageBoxes = storageRepository.findByMember(memberId);

            return useStorageBoxes;

        } catch (NoSuchElementException n) {
            System.out.println(n);
            System.out.println("안돼");
            return null;
        }
    }


    //    로그인 없이 사용자 지정 할 때 사용
    @GetMapping("memberCheck/{memberId}")
    public Result getMemberId(@PathVariable(value = "memberId") String memberId) {
        Optional<Member> member = memberRepository.findByMID(memberId);
        return new Result("ok");
//        if(member.){
//            return new Result("ok");
//        }else{
//            return new Result("no");
//        }
    }

    //    로그인 없이 매니저 확인 할 때 사용
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
        if (object.length < 1) {
            return new Optional[]{storageBoxRepository.findById(box.get().getStorageBoxCode())};
        }

        return object;
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

    // 보관함 보관소 이동
    @PostMapping("boxToBoxPay")
    private Result boxToBoxPay(@RequestBody StorageMove move) {
        Optional<Member> member = memberRepository.findByMID(move.getUserId());
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(move.getUse());
        useStorageBox.get().setUseStorageState("4");
        useStorageBoxRepository.save(useStorageBox.get());
        Optional<StorageBox> beforeBox = storageBoxRepository.findById(move.getBefore());
        beforeBox.get().setStorageBoxState("3");
        storageBoxRepository.save(beforeBox.get());
        Optional<StorageBox> afterBox = storageBoxRepository.findById(move.getAfter());
        afterBox.get().setStorageBoxState("4");
        storageBoxRepository.save(afterBox.get());


        Orders orders = new Orders(member.get());

        ordersRepository.save(orders);

        UseStorageBox newUseStorageBox = new UseStorageBox(useStorageBox.get().getUseStorageStartTime(),
                useStorageBox.get().getUseStorageEndTime(),
                "4",
                afterBox.get(), orders);
        useStorageBoxRepository.save(newUseStorageBox);
        return new Result("ok");
    }


    ////////////////////////// 상품조회  //////////////////////////

    @Autowired
    KindRepository kindRepository;


    @Autowired
    MemberEquipmentRepository memberEquipmentRepository;

    @GetMapping("myItem/{userId}")
    private List<MemberEquipment> getMyItem(@PathVariable(value = "userId")String userId){
        Optional<Member> member = memberRepository.findByMID(userId);

        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findAllByMCode(member.get());

        return memberEquipmentList;
    }

}
