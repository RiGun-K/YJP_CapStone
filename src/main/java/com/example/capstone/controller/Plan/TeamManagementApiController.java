package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamMember;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.repository.Plan.TeamRepository;
import com.example.capstone.service.TeamMemberService;
import com.example.capstone.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping
@RequiredArgsConstructor

public class TeamManagementApiController {


    private final TeamRepository teamRepository;
    private final TeamMemberService teamMemberService;
    private final TeamService teamService;



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

    @PutMapping("/api/addTeam")
    public String addTeam(@RequestBody Team tm) {
        Optional<Team> newTeamName = teamRepository.findByTeamName(tm.getTeamName());
        if (newTeamName.isEmpty()) {
            teamRepository.save(tm);
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

    @PostMapping("/api/deleteTeam")
    public String deleteTeam(@RequestBody Team team) {
        teamService.deleteTeam(team);
        return "y";
    }

    @PostMapping("/api/loginedTeamCode")
    public TeamMember loginedTeamCode(@RequestBody TeamMember teamMember) {
        return teamMemberService.loginedTeamCode(teamMember);
    }

    @GetMapping("/api/loadTeamPlans/{teamCode}")
    public List<PlanDto> loadTeamPlans(@PathVariable("teamCode") Long team) {
        return teamMemberService.loadTeamPlans(team);

    }

}