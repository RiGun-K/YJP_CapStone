package com.example.capstone.service;


import com.example.capstone.domain.Plan.Team;
import com.example.capstone.repository.Plan.TeamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    public void deleteTeam(Team team){
        teamRepository.delete(team);
    }
    public Optional<Team> loadTeamMemberList(String teamName){
        Optional<Team> teamCode = teamRepository.findByTeamName(teamName);
        return teamCode;
    }



    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
}