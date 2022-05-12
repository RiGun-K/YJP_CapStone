package com.example.capstone.dto.plan;


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
}
