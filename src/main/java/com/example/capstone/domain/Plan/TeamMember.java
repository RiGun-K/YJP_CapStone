package com.example.capstone.domain.Plan;


import com.example.capstone.domain.Member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class TeamMember {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long teamMemberCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MCODE")
    private Member mcode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAMCODE")
    private Team teamCode;

    @Column
    private char teamMemberAuthority;
    @Column
    private String acception="n";
}