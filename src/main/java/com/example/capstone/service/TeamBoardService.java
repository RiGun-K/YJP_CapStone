package com.example.capstone.service;


import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.domain.Plan.TeamBoard;
import com.example.capstone.dto.plan.MemberDto;
import com.example.capstone.dto.plan.TeamBoardDto;
import com.example.capstone.dto.plan.TeamDto;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Plan.TeamBoardRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamBoardService {
    private final TeamBoardRepository teamBoardRepository;
    private  final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public List<TeamBoardDto> insertContent(TeamBoardDto teamBoardDto) {

        TeamDto teamDto = teamBoardDto.getTeamDto();
       Long memberDto = teamBoardDto.getMemberDto();
        Optional<Team> team = teamRepository.findById(teamDto.getTeamCode());
        Optional<Member> member = memberRepository.findById(memberDto);
        System.out.println(member.get());

        TeamBoard teamBoard = new TeamBoard();
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        teamBoard.setBoardDate(localDateTime);
        teamBoard.setBoardContent(teamBoardDto.getContentDto());
        teamBoard.setTeamCode(team.get());
        teamBoard.setMcode(member.get());
        teamBoardRepository.save(teamBoard);

        List<TeamBoard> teamBoards = teamBoardRepository.findAll();
        List<TeamBoardDto> teamBoardDtos = new ArrayList<>();

        for (TeamBoard tb : teamBoards) {
            TeamBoardDto teamBoardDto1 = TeamBoardDto.builder()
                    .teamDto(new TeamDto(tb.getTeamCode()))
                    .contentDto(tb.getBoardContent())
                    .memberDto(tb.getMcode().getMCode())
                    .boardDateDto(tb.getBoardDate())
                    .build();
            teamBoardDtos.add(teamBoardDto1);
        }
        return teamBoardDtos;
    }
}
