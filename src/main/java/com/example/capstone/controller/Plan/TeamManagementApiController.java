package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamMember;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.TeamMemberDto;
import com.example.capstone.repository.Plan.TeamRepository;
import com.example.capstone.service.PlanService;
import com.example.capstone.service.TeamMemberService;
import com.example.capstone.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
//
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping
@RequiredArgsConstructor

public class TeamManagementApiController {


    private final TeamRepository teamRepository;
    private final TeamMemberService teamMemberService;
    private final TeamService teamService;
    private final PlanService planService;



    @PostMapping("/api/TeamManagementPage/{mcode}")
    public List<TeamMember> teamManagementPage(@PathVariable("mcode") Member mcode) {
      List<TeamMember> tm =  teamMemberService.teamManagementPage(mcode);
        if (tm.isEmpty()) {
            return null;
        } else return tm;
    }

    @GetMapping("/api/loadTeamMemberList/{teamName}")
    public List<TeamMember> loadTeamMemberList(@PathVariable("teamName") String teamName) {
        Optional<Team> teamCode = teamMemberService.loadTeamMemberList(teamName);
        List<TeamMember> teamMcode = teamMemberService.loadTeamMcode(teamCode.get());
        if (teamMcode.isEmpty()) {
            return null;
        } else return teamMcode;
    }

    /* 상품 결제 페이지 전 해당 상품이미지 불러오기 */
    @GetMapping(value = "/api/product_detail_image/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] imagesSearch(@PathVariable("filename") String filename, HttpServletResponse httpServletResponse) throws IOException {

       String requestPath;
        if(filename.equals("null")) {
           requestPath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets\\" + "noImg.png";
            System.out.println("1111111111111111");
        }else {
            requestPath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets\\" + filename;
            System.out.println("2222222222");
        }

        InputStream imageStream = new FileInputStream(requestPath);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }


    @PutMapping("/api/addTeam")
    public String addTeam(@RequestBody Team tm) {
        Optional<Team> newTeamName = teamRepository.findByTeamName(tm.getTeamName());
        if (newTeamName.isEmpty()) {
            teamRepository.save(tm);
            teamMemberService.autoMemberAdd(tm);
            return "y";
        } else {
            return "n";
        }
    }

    @PostMapping("api/addTeamMember")
    public String addTeamMember(@RequestBody TeamMember teamMember) {
        return teamMemberService.addTeamMember(teamMember);
    }

    @PostMapping("/api/acceptTeam")
    public String acceptTeam(@RequestBody TeamMember teamMember) {
        teamMemberService.acceptTeam(teamMember);
        return "y";
    }

    @PostMapping("/api/refuseTeam")
    public String refuseTeam(@RequestBody TeamMember teamMember) {
        teamMemberService.refuseTeam(teamMember);
        return "y";
    }

    @DeleteMapping("/api/deleteTeam")
    public void deleteTeam(@RequestParam Long teamCode) {
        teamService.deleteTeam(teamCode);

    }

    @PostMapping("/api/loginedTeamCode")
    public TeamMember loginedTeamCode(@RequestBody TeamMember teamMember) {
        return teamMemberService.loginedTeamCode(teamMember);
    }

    @GetMapping("/api/loadTeamPlans/{teamCode}")
    public List<PlanDto> loadTeamPlans(@PathVariable("teamCode") Long team) {
        return teamMemberService.loadTeamPlans(team);
    }
    @DeleteMapping("api/deletePlan")
    public void deletePlan(@RequestParam Long planCode){
        planService.deletePlan(planCode);
    }

    @DeleteMapping("api/banishment")
    public void banishment(@RequestParam Long teamMemberCode){
        teamMemberService.banishment(teamMemberCode);
    }

    @PutMapping("api/changeManager")
    public void changeManager(@RequestBody TeamMemberDto teamMemberDto){
        teamMemberService.changeManager(teamMemberDto);

    }

}
