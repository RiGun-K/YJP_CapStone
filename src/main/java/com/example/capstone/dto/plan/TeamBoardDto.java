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
    private Long memberDto;
    private String contentDto;
    private String boardDateDto;

    @Builder
    public TeamBoardDto(TeamDto teamDto, Long memberDto, String contentDto, String boardDateDto) {
        this.teamDto = teamDto;
        this.memberDto = memberDto;
        this.contentDto = contentDto;
        this.boardDateDto = boardDateDto;
    }
}
