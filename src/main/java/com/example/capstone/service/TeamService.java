package com.example.capstone.service;


import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamMember;
import com.example.capstone.repository.Plan.PlanRepository;
import com.example.capstone.repository.Plan.TeamMemberRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class TeamService {
    private final TeamRepository teamRepository;
    private final PlanRepository planRepository;
    private final PlanService planService;
    private final TeamMemberRepository teamMemberRepository;

    public void deleteTeam(Long teamCode) {
        Optional<Team> team = teamRepository.findById(teamCode);
        List<Plan> planList = planRepository.findByTeamCode(team.get());
        List<TeamMember> teamMembers = teamMemberRepository.findByteamCode(team.get());

        for (Plan p : planList) {
            planService.deletePlan(p.getPlanCode());
        }
        for (TeamMember t : teamMembers) {
            teamMemberRepository.delete(t);
        }
        teamRepository.delete(team.get());
    }

    public Optional<Team> loadTeamMemberList(String teamName) {
        Optional<Team> teamCode = teamRepository.findByTeamName(teamName);
        return teamCode;
    }


}