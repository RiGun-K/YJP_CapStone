package com.example.capstone.controller.Member;

import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Company;
import com.example.capstone.domain.Member.MailCheck;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Member.PhCheck;
import com.example.capstone.domain.Product.Kind;
import com.example.capstone.domain.storage.MemberEquipment;
import com.example.capstone.repository.Board.BoardRepository;
import com.example.capstone.repository.Member.CompanyRepository;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.KindRepository;
import com.example.capstone.repository.Storage.MemberEquipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CompanyRepository companyRepository;

    ///게시글///
    @Autowired
    private BoardRepository boardRepository;

    ///레포지토리 테스트///
    @Autowired
    private MemberEquipmentRepository memberEquipmentRepository;

    @Autowired
    private KindRepository kindRepository;

    //////로그인 부분///////
    @PostMapping("/login")
    public Member login(@RequestBody HashMap<String, String> loginMem){
        Optional<Member> member = memberRepository.findByMID(loginMem.get("MID"));
        if(member.isEmpty()){
            return null;
        }

        if(member.get().getMPass().toString().equals(loginMem.get("MPASS"))){
            return member.get();
        }else{
            return null;
        }
    }

    ///회원가입///
    @PostMapping ("/signUp")
    public Boolean signUp(@RequestBody HashMap<String, String> signUp){
        Optional<Member> memberCh = memberRepository.findByMID(signUp.get("MID"));
        if(!memberCh.isEmpty()){
            return false;
        }

        Member member = new Member();
        member.setMID(signUp.get("MID"));
        member.setMPass(signUp.get("MPASS"));
        member.setMNick(signUp.get("MNick"));
        member.setMZadd(signUp.get("MZadd"));
        member.setMRadd(signUp.get("MRadd"));
        member.setMAdd(signUp.get("MAdd"));
        member.setMPH(signUp.get("MPH"));
        member.setMMail(signUp.get("MEmail"));
        member.setMSC(signUp.get("MSC"));
        member.setMname(signUp.get("Mname"));
        member.setMSD(LocalDate.now().toString());

        memberRepository.save(member);
        return true;
    }

    ///회원상태 변경///
    @PostMapping("chagneMSC")
    public Boolean changeMSC(@RequestBody HashMap<String, String> updataData){
        Optional<Member> memberCh = memberRepository.findByMID(updataData.get("MID"));

        if(memberCh.isEmpty()){
            return false;
        }

        memberCh.get().setMSC(updataData.get("MSC"));
        memberRepository.save(memberCh.get());
        return true;
    }

    ///회원 수정///
    @PostMapping("memberUpdate")
    public Boolean memberUpdate(@RequestBody HashMap<String, String> updataData){
        Optional<Member> memberCh = memberRepository.findByMID(updataData.get("MID"));

        if(memberCh.isEmpty()){
            return false;
        }

        memberCh.get().setMNick(updataData.get("MNick"));
        memberCh.get().setMZadd(updataData.get("MZadd"));
        memberCh.get().setMZadd(updataData.get("MRadd"));
        memberCh.get().setMAdd(updataData.get("MAdd"));
        memberCh.get().setMPH(updataData.get("MPH"));
        memberCh.get().setMMail(updataData.get("MEmail"));
        memberRepository.save(memberCh.get());

        return true;
    }

    ///회원탈퇴-실제 데이터 삭제는 없다(상태변경)///
    @PostMapping("deleteMem")
    public Boolean deleteMem(@RequestBody HashMap<String, String> updataData){
        Optional<Member> memberCh = memberRepository.findByMID(updataData.get("MID"));

        if(memberCh.isEmpty()){
            return false;
        }

        memberCh.get().setMSC("0");
        memberCh.get().setMLD(LocalDate.now().toString());
        memberRepository.save(memberCh.get());

        return true;
    }


    ///속성 체크///
    ///유저확인///
    @PostMapping("/userCheck")
    public Boolean userCheck(@RequestBody HashMap<String, String> signUp) {
        Optional<Member> memberCh = memberRepository.findByMID(signUp.get("MID"));
        if (memberCh.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }
    ///닉네임확인///
    @PostMapping ("/nameCheck")
    public Boolean nameCheck(@RequestBody HashMap<String, String> signUp) {
        Optional<Member> memberCh = memberRepository.findByMNick(signUp.get("MNick"));

        if (memberCh.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }
    ///회사확인///
    @PostMapping("companyCodeCheck")
    public Boolean companyCodeCheck(@RequestBody HashMap<String, String> company){
        Optional<Company> companyList = companyRepository.findCompanyByCCode(company.get("CCode"));

        if(companyList.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    ///회원 아이디의 전화번호 확인///
    @PostMapping("phequalCheck")
    public Boolean phequalCheck(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMID(body.get("MID"));
        if(member.get().getMPH().equals(body.get("MPH"))){
            return true;
        }else{
            return false;
        }
    }

    ///판매자 신청///
    @PostMapping("signCompany")
    public Boolean signCompany(@RequestBody HashMap<String, String> signUp){
        Optional<Member> memberCh = memberRepository.findByMID(signUp.get("MID"));
        Optional<Company> com = companyRepository.findCompanyByMember(memberCh.get());
        if(!com.isEmpty()){
            return false;
        }

        Company company = new Company();
        company.setMember(memberCh.get());
        company.setCCode(signUp.get("CCode"));
        company.setCName(signUp.get("CName"));
        company.setCEO(signUp.get("CEO"));
        company.setCZadd(signUp.get("CZadd"));
        company.setCRadd(signUp.get("CRadd"));
        company.setCAdd(signUp.get("CAdd"));
        company.setCph(signUp.get("Cph"));
        company.setCEmail(signUp.get("CEmail"));
        company.setChp(signUp.get("Chp"));
        company.setCst(LocalDate.now().toString());
        company.setCsc("1");

        memberCh.get().setMSC("2");
        memberRepository.save(memberCh.get());
        companyRepository.save(company);
        return true;
    }

    ///로그인 여부 확인///
    @PostMapping("loginCheck")
    public Boolean loginCheck(@RequestBody HashMap<String, String> check){
        Optional<Member> memberCh = memberRepository.findByMID(check.get("MID"));
        if(!memberCh.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    ///테이블 조회///
    @PostMapping("getMember")
    public Member getMember(@RequestBody HashMap<String, String> check){
        Optional<Member> memberCh = memberRepository.findByMID(check.get("MID"));
        if(!memberCh.isEmpty()){
            return memberCh.get();
        }else {
            return null;
        }
    }
    @GetMapping("getAllmember")
    public List<Member> getAllmember(){
        List<Member> members = memberRepository.findAll();

        return members;
    }


    /////////판매자 신청 부분////////
    @GetMapping("getRequestCompany")
    public List<Company> getAllRequest(){
        List<Company> companyList = companyRepository.findAll();

        return companyList;
    }

    @PostMapping("acceptCompany")
    public void acceptCompany(@RequestBody HashMap<String, String> check){
        Optional<Company> company = companyRepository.findCompanyByCCode(check.get("CCode"));
        company.get().setCsc("2");
        Optional<Member> members = memberRepository.findByMID(company.get().getMember().getMID());
        members.get().setMSC("4");
        companyRepository.save(company.get());
        memberRepository.save(members.get());
    }
    @Autowired
    private final MailCheck mailCheck;

    ///인증메일 처리///
    @PostMapping("mailCheck")
    public int mailCheck(@RequestBody HashMap<String, String> check){
        mailCheck.setAddress(check.get("email"));
        mailCheck.mailSend();

        return mailCheck.getAutoInt();
    }

    ///휴대전화 처리///
    @PostMapping("phCheck")
    public int phCheck(@RequestBody HashMap<String, String> pNumber){
        PhCheck phCheck = new PhCheck();
        phCheck.setFromClient(pNumber.get("MPH"));
        return phCheck.sendAuth();
    }

    ///아이디찾기///
    @PostMapping("checkID")
    public String checkID(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMPH(body.get("MPH"));
        if(member.isEmpty()){
            return "아이디가 존재하지 않습니다";
        }else{
            return "사용자의 아이디는 " + member.get().getMID() + "입니다";
        }
    }

    ///비밀번호찾기///
    @PostMapping("checkPass")
    public String checkPass(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMID(body.get("MID"));
        mailCheck.setAddress(member.get().getMMail());
        mailCheck.passSend();
        member.get().setMPass(Integer.toString(mailCheck.getAutoInt()));
        memberRepository.save(member.get());
        return "사용자 이메일로 임시비밀번호를 전송했습니다";
    }

    ///비밀번호 변경///
    @PostMapping("changePass")
    public Boolean changePass(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMCode(Long.parseLong(body.get("MCODE")));

        if(body.get("currentPass").equals(body.get("changePass"))){
         return false;
        }
        else if(member.get().getMPass().equals(body.get("currentPass"))){
            member.get().setMPass(body.get("changePass"));
            memberRepository.save(member.get());
            return true;
        }else{
            return false;
        }
    }

    ///마이페이지 부분///

    ///내장비페이지///
    @PostMapping("myPageEquip")
    public List<MemberEquipment> myPageEquip(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMCode(Long.parseLong(body.get("MID")));
        List<MemberEquipment> memberEquipmentList = memberEquipmentRepository.findAllByMCode(member.get());

        return  memberEquipmentList;
    }

    ///분류조회///
    @GetMapping("allKindGet")
    public List<Kind> allKindGet(){
        List<Kind> kindList = kindRepository.findAll();
        return kindList;
    }

    ///장비추가///
    @PostMapping("addMyEquip")
    public Boolean addMyEquip(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMCode(Long.parseLong(body.get("MID")));
        Optional<Kind> kind = kindRepository.findByKindname(body.get("KindName"));
        MemberEquipment memberEquipment = new MemberEquipment();
        memberEquipment.setMemEquipmentName(body.get("NAME"));
        memberEquipment.setKindid(kind.get());
        memberEquipment.setMCode(member.get());
        memberEquipment.setMemEquipmentCount(Integer.parseInt(body.get("Count")));
        memberEquipment.setMemEquipmentState("0");
        memberEquipmentRepository.save(memberEquipment);
        return true;
    }

    ///내게시글페이지///
    @PostMapping("myWritter")
    public List<Writer> myPageWritter(@RequestBody HashMap<String, String> body){
        Optional<Member> member = memberRepository.findByMCode(Long.parseLong(body.get("MID")));
        List<Writer> writerList = boardRepository.findByMID(member.get());

        return writerList;
    }


    ///테스트///
    @PostMapping("testCheck")
    public void testCheck(@RequestBody String a){
       System.out.println(a);
    }
}
