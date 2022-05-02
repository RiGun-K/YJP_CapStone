package com.example.capstone.domain.Plan;


import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PlanTag {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long TagCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_CODE")
    private Plan plan;

    @Column
    private String TagContent;

}
