package com.example.capstone.controller.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.order.OrderMenu;
import com.example.capstone.domain.order.Orders;
import com.example.capstone.domain.storage.*;
import com.example.capstone.dto.storage.*;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingAreaRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Product.MenuBuyRepository;
import com.example.capstone.repository.Storage.*;
import com.example.capstone.repository.orders.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.capstone.domain.order.*;
import com.example.capstone.repository.orders.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UseStorageController {
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
    MenuBuyRepository menuBuyRepository;

    @Autowired
    OrderMenuRepository orderMenuRepository;

    @Autowired
    BoxItemRepository boxItemRepository;

    //보관함 연장 결제
    @PostMapping("/renewalPay")
    public Result renewalPay(@RequestBody RenewalBox renewalBox) {
        Optional<UseStorageBox> beforeUseStorageBox = useStorageBoxRepository.findById(renewalBox.getUseBoxCode());
        Optional<Member> user = memberRepository.findByMCode(beforeUseStorageBox.get().getMCode().getMCode());
        Optional<StorageBox> storageBox = storageBoxRepository.findById(beforeUseStorageBox.get().getStorageBoxCode().getStorageBoxCode());

        List<BoxItem> boxItemList = boxItemRepository.findByUseStorageBoxCode(beforeUseStorageBox.get());
        for (int i = 0; i < boxItemList.size(); i++) {
            boxItemList.get(i).setUseStorageBoxCode(null);
        }
        LocalDate start = renewalBox.getStartTime();
        LocalDate end = renewalBox.getEndTime();

        Orders orderList = new Orders(user.get());
        orderList.setOrderPrice(renewalBox.getPrice());
        orderList.setPaymentDate(LocalDateTime.now());
        ordersRepository.save(orderList);

        beforeUseStorageBox.get().setUseStorageState("1");
        useStorageBoxRepository.save(beforeUseStorageBox.get());

        UseStorageBox useStorageBox = new UseStorageBox();
        useStorageBox.setStorageBoxCode(storageBox.get());
        useStorageBox.setOrderCode(orderList);
        useStorageBox.setUseStorageStartTime(start);
        useStorageBox.setUseStorageEndTime(end);
        useStorageBox.setMCode(user.get());
        useStorageBox.setUseStorageState("2");
        for (int i = 0; i < boxItemList.size(); i++) {
            boxItemList.get(i).setUseStorageBoxCode(useStorageBox);
            boxItemRepository.save(boxItemList.get(i));
        }
        useStorageBoxRepository.save(useStorageBox);
//         박스 상태 변화
        storageBox.get().setStorageBoxState("2");
//         결제된 박스 업데이트
        storageBoxRepository.save(storageBox.get());

        return new Result("ok");
    }

    //보관함 계약 해지
    @PostMapping("/closeBox/{useBoxCode}")
    public Result closeBox(@PathVariable(value = "useBoxCode") long useBoxCode) {
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(useBoxCode);
        long boxCode = useStorageBox.get().getStorageBoxCode().getStorageBoxCode();
        Optional<StorageBox> storageBox = storageBoxRepository.findById(boxCode);

        UseStorageBox useBox = useStorageBox.get();
        StorageBox box = storageBox.get();

        useBox.setUseStorageState("1");
        box.setStorageBoxState("6");

        useStorageBoxRepository.save(useBox);
        storageBoxRepository.save(box);

        return new Result("ok");
    }

    // 보관함 결제
    @PostMapping("/payBox")
    public Result payStorageBox(@RequestBody payStorageBox payStorageBox) {
        Optional<Member> user = memberRepository.findByMID(payStorageBox.getUserId());
        Optional<StorageBox> storageBox = storageBoxRepository.findById(payStorageBox.getStorageBoxCode());
        Optional<Storage> storage = storageRepository.findByStorageBoxCode(storageBox.get().getStorageBoxCode());
        LocalDate start = payStorageBox.getUseStorageStartTime();

        LocalDate end = payStorageBox.getUseStorageEndTime();

        Orders orders = new Orders(user.get(), payStorageBox.getPrice());
        orders.setPaymentDate(LocalDateTime.now());
        orders.setDeliveryAddress(storage.get().getStorageAddress());
        orders.setDeliveryZipcode(storage.get().getStorageZipcode());
        orders.setDeliveryGetterTel(storage.get().getStorageTel());
        orders.setOrderState("2");
        ordersRepository.save(orders);

        UseStorageBox useStorageBox = new UseStorageBox();
        useStorageBox.setUseStorageStartTime(start);
        useStorageBox.setUseStorageEndTime(end);
        useStorageBox.setStorageBoxCode(storageBox.get());
        useStorageBox.setOrderCode(orders);
        useStorageBox.setMCode(user.get());
        useStorageBox.setUseStorageState("2");
        useStorageBoxRepository.save(useStorageBox);

        if (payStorageBox.getItemDTOList().size() > 0) {
            for (int i = 0; i < payStorageBox.getItemDTOList().size(); i++) {
                Optional<MemberEquipment> memberEquipment = memberEquipmentRepository.findById(payStorageBox.getItemDTOList().get(i).getItemCode());
                BoxItem boxItem = new BoxItem();
                boxItem.setUseStorageBoxCode(useStorageBox);
                boxItem.setMemEquipmentCode(memberEquipment.get());
                boxItem.setBoxItemCount(payStorageBox.getItemDTOList().get(i).getCount());
                boxItemRepository.save(boxItem);
            }
        } else {
            System.out.println("없다");
        }
        // 박스 상태 변화
        storageBox.get().setStorageBoxState("2");

        System.out.println("123");
        // 결제된 박스 업데이트
        storageBoxRepository.save(storageBox.get());

        return new Result("ok");

    }

    // 보관함 장소 이동
    @PostMapping("roundMoveBox")
    private Result roundMovePay(@RequestBody RoundMove roundMove) {
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(roundMove.getUseBoxCode());
        if (useStorageBox.get().getUseStorageState().equals("2")) {


            StorageBox storageBox = useStorageBox.get().getStorageBoxCode();
            storageBox.setStorageBoxState("7");
            storageBoxRepository.save(storageBox);

            Optional<Member> member = memberRepository.findByMID(roundMove.getUserId());

            Orders orderList = new Orders(member.get());
            orderList.setDeliveryZipcode(roundMove.getZipCode());
            orderList.setDeliveryAddress(roundMove.getAddress() + roundMove.getDetailAddress());
            ordersRepository.save(orderList);

            useStorageBox.get().setUseStorageState("9"+ orderList.getOrderCode());
            useStorageBoxRepository.save(useStorageBox.get());
            return new Result("ok");
        } else if (useStorageBox.get().getUseStorageState() == "1") {
            return new Result("umm");
        } else {
            return new Result("no");
        }

    }

    // 보관함 장소 이동
    @PostMapping("homeToMovePay")
    private Result homeMovePay(@RequestBody HomeModeDTO homeModeDTO) {
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(homeModeDTO.getUseBoxCode());
        if (useStorageBox.get().getUseStorageState().equals("2")) {


            StorageBox storageBox = useStorageBox.get().getStorageBoxCode();
            storageBox.setStorageBoxState("7");
            storageBoxRepository.save(storageBox);

            Orders orderList = new Orders(homeModeDTO.getMember());
            orderList.setDeliveryZipcode(homeModeDTO.getMember().getMZadd());
            orderList.setDeliveryAddress(homeModeDTO.getMember().getMAdd() + homeModeDTO.getMember().getMRadd());
            ordersRepository.save(orderList);

            useStorageBox.get().setUseStorageState("9"+ orderList.getOrderCode());
            useStorageBoxRepository.save(useStorageBox.get());
            return new Result("ok");
        } else if (useStorageBox.get().getUseStorageState() == "1") {
            return new Result("umm");
        } else {
            return new Result("no");
        }

    }

    //    사용중인 보관함 시간 조회
    @GetMapping("findUseBoxTimes/{stCode}/{boxCode}/{mid}")
    public LocalDateTime[][] findUseBoxTimes(@PathVariable(value = "stCode") long stCode,
                                             @PathVariable(value = "boxCode") long boxCode,
                                             @PathVariable(value = "mid") String mid) throws ParseException {
        Optional<Member> member = memberRepository.findByMID(mid);

        List<List<String>> useStorageBoxList = useStorageBoxRepository.findByUseTimes(stCode, boxCode, member.get().getMCode());

        int x = useStorageBoxList.size();
        int y = 0;
        for (int i = 0; i < useStorageBoxList.size(); i++) {
            for (int j = 0; j < useStorageBoxList.get(i).size(); j++) {
                if (i == 0) {
                    y = useStorageBoxList.get(i).size();
                }
                if (i > 0 && useStorageBoxList.get(i).size() > useStorageBoxList.get(i - 1).size()) {
                    y = useStorageBoxList.get(i).size();
                }
            }
        }

        LocalDateTime[][] array = new LocalDateTime[x][y];
        for (int i = 0; i < useStorageBoxList.size(); i++) {
            for (int j = 0; j < useStorageBoxList.get(i).size(); j++) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");
                LocalDateTime dateTime = LocalDateTime.parse(useStorageBoxList.get(i).get(j), formatter);
                array[i][j] = dateTime;
            }
        }
        return array;
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
        Orders orders = new Orders();
        orders.setPaymentDate(orderTime);
        orders.setMCode(member.get());
        orders.setOrderPrice(move.getPrice());
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
        UseStorageBox newUseStorageBox = new UseStorageBox(orderTime.toLocalDate(),
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
    private Result moveStateUpdate(@PathVariable(value = "beforeBox") long beforeBox, @PathVariable(value = "afterBox") long afterBox) {
        Optional<UseStorageBox> beforeUSBox = useStorageBoxRepository.findById(beforeBox);
        Optional<UseStorageBox> afterUSBox = useStorageBoxRepository.findById(afterBox);
        List<BoxItem> boxItemList = boxItemRepository.findByUseStorageBoxCode(beforeUSBox.get());

        for (int i = 0; i < boxItemList.size(); i++) {
            boxItemList.get(i).setUseStorageBoxCode(afterUSBox.get());
            boxItemRepository.save(boxItemList.get(i));
        }

        LocalDate nowTime = LocalDate.now();

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
    private Result endmoveUpdate(@PathVariable(value = "afterBox") long afterBox) {
        Optional<UseStorageBox> afterUSBox = useStorageBoxRepository.findById(afterBox);

        afterUSBox.get().setUseStorageState("2");

        StorageBox storageBox = afterUSBox.get().getStorageBoxCode();
        storageBox.setStorageBoxState("2");

        storageBoxRepository.save(storageBox);
        useStorageBoxRepository.save(afterUSBox.get());

        return new Result("ok");
    }

//    이동지 정보조회
    @GetMapping("delInfo/{orcode}")
    private Orders getDelInfo(@PathVariable(value = "orcode")int code){
        Optional<Orders> orders = ordersRepository.findById(code);
        return orders.get();
    }

    // 내가 사용중인 보관함 조회
    @GetMapping("getUseBox/{useBoxCode}")
    private UseStorageBox getUseBox(@PathVariable(value = "useBoxCode") long useBoxCode) {
        Optional<UseStorageBox> useStorageBox = useStorageBoxRepository.findById(useBoxCode);

        return useStorageBox.get();
    }

    // 보관함에 보관안하는 내장비 조회
    @GetMapping("myItem/{userId}")
    private List<MemberEquipment> myItem(@PathVariable(value = "userId") String userId) {
        Optional<Member> member = memberRepository.findByMID(userId);
        long mCode = member.get().getMCode();
        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findByMemEquipment(mCode);

        return memberEquipmentList;
    }

    ////////////////////////// 수리상품 조회 ////////////////////////

    @GetMapping("repairItemList")
    private List<MenuBuy> getRepairList() {
        List<MenuBuy> menuBuyList = menuBuyRepository.findByRepairList();
        return menuBuyList;
    }

    @GetMapping("PickRepairList/{kindId}")
    private List<MenuBuy> getPickRepairList(@PathVariable(value = "kindId") int kindId) {
        List<MenuBuy> menuBuyList = menuBuyRepository.findBykindId(kindId);
        return menuBuyList;
    }

    @GetMapping("searchRepairList/{searchText}/{groupKindId}")
    private List<MenuBuy> getSearchRepairList(@PathVariable(value = "searchText") String search,
                                              @PathVariable(value = "groupKindId") int kindId) {

        List<MenuBuy> menuBuyList;
        if (kindId == 0) {
            menuBuyList = menuBuyRepository.findBySearchName(search);
        } else {
            menuBuyList = menuBuyRepository.findByNameAndKindid(search, kindId);
        }
        if (menuBuyList.isEmpty()) {
            return null;
        }
        return menuBuyList;
    }

    //    장비수리 신청 결제
    @PostMapping("postCarePay")
    private Result postCarePay(@RequestBody CareListPayDTO care) {
        Optional<Member> member = memberRepository.findByMID(care.getMid());
        Member member1 = member.get();
        Orders orders = new Orders();
        orders.setOrderPrice(care.getPrice());
        orders.setPaymentDate(LocalDateTime.now());
        orders.setMCode(member1);
        orders.setDeliveryRequest(care.getText());
        ordersRepository.save(orders);
        for (int i = 0; i < care.getList().size(); i++) {
            Optional<MenuBuy> menuBuy = menuBuyRepository.findById(care.getList().get(i).getBuyId());
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setOrderMenuCount(1);
            orderMenu.setMenuBuy(menuBuy.get());
            orderMenu.setOrders(orders);
            orderMenuRepository.save(orderMenu);

            Optional<MemberEquipment> memberEquipment = memberEquipmentRepository.findById(care.getList().get(i).getMemEquipmentCode());
            MemberEquipment memberEquipment1 = memberEquipment.get();
            memberEquipment1.setMemEquipmentState("2");
            memberEquipmentRepository.save(memberEquipment1);
//
//            UseStorageBox useStorageBox = memberEquipment.get().getUseStorageBoxCode();
//            useStorageBox.setUseStorageState("6");
//            useStorageBoxRepository.save(useStorageBox);
        }
        return new Result("ok");
    }

}

