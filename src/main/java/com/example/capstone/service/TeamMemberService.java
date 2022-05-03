package com.example.capstone.service;


import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamMember;
import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.TeamDto;
import com.example.capstone.repository.Plan.PlanRepository;
import com.example.capstone.repository.Plan.TeamMemberRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import lombok.RequiredArgsConstructor;
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
        System.out.println("받아짐");
        List<TeamMember> acceptionCheck = teamMemberRepository.findByTeamCodeAndMcode(teamMember.getTeamCode(), teamMember.getMcode());
        teamMemberRepository.delete(acceptionCheck.get(0));
        System.out.println("거절성공");
    }

    public String addTeamMember(TeamMember teamMember) {

        List<TeamMember> checkMember = teamMemberRepository.findByTeamCodeAndMcode(teamMember.getTeamCode(), teamMember.getMcode());
        if (checkMember.isEmpty()) {
            teamMemberRepository.save(teamMember);
            return "y";
        } else {
            System.out.println("존재하는 회원입니다");
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
            // teamDto 만들기
            Team teamEntity = plan.getTeamCode();
            TeamDto teamDto = TeamDto.builder()
                    .teamCode(teamEntity.getTeamCode())
                    .teamName(teamEntity.getTeamName())
                    .teamState(teamEntity.getTeamState())
                    .teamMaster(teamEntity.getTeamMaster()).build();
            //PlanDto 만들기
            PlanDto planDto = PlanDto.builder()
                    .planCode(plan.getPlanCode())
                    .teamCode(teamDto)
                    .planName(plan.getPlanName())
                    .planBudget(plan.getPlanBudget())
                    .planDestination(plan.getPlanDestination())
                    .planEnd(plan.getPlanEnd())
                    .planNumber(plan.getPlanNumber())
                    .planOpen(plan.getPlanOpen())
                    .planStart(plan.getPlanStart())
                    .planViews(plan.getPlanViews())
                    .planUsedCount(plan.getPlanUsedCount())
                    .planTotalDate(plan.getPlanTotalDate())
                    .planType(plan.getPlanType())
                    .build();
            // List에 저장
            planDtos.add(planDto);
        }
        return planDtos;

    }
}