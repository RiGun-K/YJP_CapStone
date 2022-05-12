package com.example.capstone.domain.Plan;


import com.example.capstone.domain.Member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class PlanTag {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long TagCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_CODE")
    private Plan plan;

    @Column
    private String tagContent;

}
