package com.example.capstone.domain.Plan;


import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="TEAMCODE")
    private Long teamCode;

    @Column
    private String teamName;
    @Column
    private char teamState;
    @Column
    private Long teamMaster;





}