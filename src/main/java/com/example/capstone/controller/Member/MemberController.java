package com.example.capstone.controller.Member;

import com.example.capstone.domain.Member.Company;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Member.PhCheck;
import com.example.capstone.repository.Member.CompanyRepository;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.domain.Member.MailCheck;
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


    ///속성 체크//

    @PostMapping("/userCheck")
    public Boolean userCheck(@RequestBody HashMap<String, String> signUp) {
        Optional<Member> memberCh = memberRepository.findByMID(signUp.get("MID"));
        if (memberCh.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }

    @PostMapping ("/nameCheck")
    public Boolean nameCheck(@RequestBody HashMap<String, String> signUp) {
        Optional<Member> memberCh = memberRepository.findByMNick(signUp.get("MNick"));

        if (memberCh.isEmpty()) {
            return true;
        }else{
            return false;
        }
    }

    @PostMapping("companyCodeCheck")
    public Boolean companyCodeCheck(@RequestBody HashMap<String, String> company){
        Optional<Company> companyList = companyRepository.findCompanyByCCode(company.get("CCode"));

        if(companyList.isEmpty()){
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
        System.out.println(company.get().getMember().getMID());
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
}
