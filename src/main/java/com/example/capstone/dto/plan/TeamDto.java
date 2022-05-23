package com.example.capstone.dto.plan;


import com.example.capstone.domain.Plan.Team;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamDto {
    private Long teamCode;
    private String teamName;
    private char teamState;
    private Long teamMaster;

    @Builder
    public TeamDto(Long teamCode, String teamName, char teamState, Long teamMaster) {
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.teamState = teamState;
        this.teamMaster = teamMaster;
    }

    public TeamDto(Team teamCode) {
        this.teamCode = teamCode.getTeamCode();
        this.teamName = teamCode.getTeamName();
        this.teamState = teamCode.getTeamState();
        this.teamMaster = teamCode.getTeamMaster();
    }
}
