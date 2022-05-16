package com.example.capstone.dto.plan;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Team;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeamBoardDto {
    private TeamDto teamDto;
    private MemberDto memberDto;
    private String contentDto;
    private String boardDateDto;
    private Long teamBoardCodeDto;

    @Builder
    public TeamBoardDto(TeamDto teamDto, MemberDto memberDto, String contentDto, String boardDateDto, Long teamBoardCodeDto) {
        this.teamDto = teamDto;
        this.memberDto = memberDto;
        this.contentDto = contentDto;
        this.boardDateDto = boardDateDto;
        this.teamBoardCodeDto = teamBoardCodeDto;
    }
}
