package com.example.capstone.dto.plan;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Plan.Team;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeamMemberDto {
    private Long teamMemberCode;
    private Member mcode;
    private TeamDto teamCode;
    private char teamMemberAuthority;
    private String acception="n";

    @Builder
    public TeamMemberDto(Long teamMemberCode, Member mcode, TeamDto teamCode, char teamMemberAuthority, String acception) {
        this.teamMemberCode = teamMemberCode;
        this.mcode = mcode;
        this.teamCode = teamCode;
        this.teamMemberAuthority = teamMemberAuthority;
        this.acception = acception;
    }
}
