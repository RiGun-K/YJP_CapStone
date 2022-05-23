package com.example.capstone.domain.Plan;


import com.example.capstone.domain.Member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeamBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long teamBoardCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "MCODE")
    private Member mcode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAMCODE")
    private Team teamCode;

    @Column
    private String boardContent;
    @Column
    private String boardDate;



}
