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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamBoardService {
    private final TeamBoardRepository teamBoardRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    public void insertContent(TeamBoardDto teamBoardDto) {

        TeamDto teamDto = teamBoardDto.getTeamDto();
        MemberDto memberDto = teamBoardDto.getMemberDto();
        Optional<Team> team = teamRepository.findById(teamDto.getTeamCode());
        Optional<Member> member = memberRepository.findById(memberDto.getMCodeDto());
        System.out.println(member.get());

        TeamBoard teamBoard = new TeamBoard();
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        teamBoard.setBoardDate(localDateTime);
        teamBoard.setBoardContent(teamBoardDto.getContentDto());
        teamBoard.setTeamCode(team.get());
        teamBoard.setMcode(member.get());
        teamBoardRepository.save(teamBoard);

//        List<TeamBoard> teamBoards = teamBoardRepository.findAll();
//        List<TeamBoardDto> teamBoardDtos = new ArrayList<>();
//
//        for (TeamBoard tb : teamBoards) {
//            TeamBoardDto teamBoardDto1 = TeamBoardDto.builder()
//                    .teamDto(new TeamDto(tb.getTeamCode()))
//                    .contentDto(tb.getBoardContent())
//                    .memberDto(new MemberDto(tb.getMcode()))
//                    .boardDateDto(tb.getBoardDate())
//                    .build();
//            teamBoardDtos.add(teamBoardDto1);
//        }
//        return teamBoardDtos;
    }

    public Page<TeamBoardDto> loadTeamBoards(Long teamDto, Pageable pageable) {
        Optional<Team> team = teamRepository.findById(teamDto);
        Page<TeamBoard> teamBoards = teamBoardRepository.findByTeamCodeOrderByBoardDate(team.get(),pageable);
        Page<TeamBoardDto> teamBoardsDtoPage = teamBoards.map(tb -> TeamBoardDto.builder()
                .teamDto(new TeamDto(tb.getTeamCode()))
                .contentDto(tb.getBoardContent())
                .memberDto(new MemberDto(tb.getMcode()))
                .boardDateDto(tb.getBoardDate())
                .teamBoardCodeDto(tb.getTeamBoardCode())
                .build());

//        List<TeamBoardDto> teamBoardDtos = new ArrayList<>();
//
//        for (TeamBoard tb : teamBoards.getContent()) {
//            TeamBoardDto teamBoardDto1 = TeamBoardDto.builder()
//                    .teamDto(new TeamDto(tb.getTeamCode()))
//                    .contentDto(tb.getBoardContent())
//                    .memberDto(new MemberDto(tb.getMcode()))
//                    .boardDateDto(tb.getBoardDate())
//                    .teamBoardCodeDto(tb.getTeamBoardCode())
//                    .build();
//            teamBoardDtos.add(teamBoardDto1);
//        }
        return teamBoardsDtoPage;
    }

    public void editContent(TeamBoardDto teamBoardDto) {
        String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        Optional<TeamBoard> teamBoard = teamBoardRepository.findById(teamBoardDto.getTeamBoardCodeDto());
        teamBoard.get().setBoardContent(teamBoardDto.getContentDto());
        teamBoard.get().setBoardDate(localDateTime);
        teamBoardRepository.save(teamBoard.get());
    }
    public void deleteContent(Long teamBoardDto){
        Optional<TeamBoard> teamBoard = teamBoardRepository.findById(teamBoardDto);
        teamBoardRepository.delete(teamBoard.get());
    }
}
