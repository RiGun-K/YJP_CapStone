package com.example.capstone.service;


import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamMember;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.TeamDto;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Plan.PlanRepository;
import com.example.capstone.repository.Plan.TeamMemberRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.MessageWriter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class TeamMemberService {
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository;

//

    public List<TeamMember> teamManagementPage(Member mcode) {
        List<TeamMember> tm = teamMemberRepository.findByMcode(mcode);
        return tm;
    }

    public Optional<Team> loadTeamMemberList(String teamName) {
        Optional<Team> teamCode = teamRepository.findByTeamName(teamName);
        return teamCode;
    }

    public List<TeamMember> loadTeamMcode(Team team) {
        List<TeamMember> tm = teamMemberRepository.findByteamCode(team);
        return tm;
    }

    public void acceptTeam(TeamMember teamMember) {

        List<TeamMember> acceptionCheck = teamMemberRepository.findByTeamCodeAndMcode(teamMember.getTeamCode(), teamMember.getMcode());
        acceptionCheck.get(0).setAcception("y");
        teamMemberRepository.findById(acceptionCheck.get(0).getMcode().getMCode());
        teamMemberRepository.save(acceptionCheck.get(0));
    }

    public void refuseTeam(TeamMember teamMember) {
        List<TeamMember> acceptionCheck = teamMemberRepository.findByTeamCodeAndMcode(teamMember.getTeamCode(), teamMember.getMcode());
        teamMemberRepository.delete(acceptionCheck.get(0));
    }

    public String addTeamMember(TeamMember teamMember) {
        List<TeamMember> checkMember = teamMemberRepository.findByTeamCodeAndMcode(teamMember.getTeamCode(), teamMember.getMcode());
        if (checkMember.isEmpty()) {
            teamMemberRepository.save(teamMember);
            return "y";
        } else {
            return "n";
        }
    }

    public TeamMember loginedTeamCode(TeamMember teamMember) {
        List<TeamMember> tm = teamMemberRepository.findByTeamCodeAndMcode(teamMember.getTeamCode(), teamMember.getMcode());
        return tm.get(0);
    }

    public List<PlanDto> loadTeamPlans(Long team) {
        List<Plan> plans = planRepository.findAllByTeamCodeTeamCode(team);
        List<PlanDto> planDtos = new ArrayList<>();
        for (Plan plan : plans) {
            planDtos.add(plan.toPlanDto());
        }
        return planDtos;
    }
    public void autoMemberAdd(Team tm){
        Optional<Member> member = memberRepository.findById(tm.getTeamMaster());
        System.out.println("ddddddddd");
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamCode(tm);
        teamMember.setMcode(member.get());
        teamMember.setAcception("y");
        System.out.println(teamMember.getTeamCode());
        teamMemberRepository.save(teamMember);
    }
}
