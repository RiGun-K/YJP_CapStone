package com.example.capstone.domain.Plan;


import com.example.capstone.dto.plan.PlanDto;
import com.example.capstone.dto.plan.TeamDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Plan {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long planCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAMCODE")
    private Team teamCode;

    @Column
    private String planDestination;
    @Column
    private String planName;
    @Column
    private int planBudget;
    @Column
    private String planType;
    @Column
    private String planStart;
    @Column
    private String planEnd;
    @Column
    private String planOpen;
    @Column
    private int planNumber;
    @Column
    private int planTotalDate;
    @Column
    private Integer planViews = 0;
    @Column
    private Integer planUsedCount = 0;
    @Column
    private String address;
    @Column
    private String detailAddress;
    @Column
    private String campingName;


    public PlanDto toPlanDto() {

        Team teamEntity = this.getTeamCode();
        TeamDto teamDto = TeamDto.builder()
                .teamCode(teamEntity.getTeamCode())
                .teamName(teamEntity.getTeamName())
                .teamState(teamEntity.getTeamState())
                .teamMaster(teamEntity.getTeamMaster()).build();

        PlanDto planDto = PlanDto.builder()
                .planCode(this.getPlanCode())
                .teamCode(teamDto)
                .planName(this.getPlanName())
                .planBudget(this.getPlanBudget())
                .planDestination(this.getPlanDestination())
                .planEnd(this.getPlanEnd())
                .planNumber(this.getPlanNumber())
                .planOpen(this.getPlanOpen())
                .planStart(this.getPlanStart())
                .planViews(this.getPlanViews())
                .planUsedCount(this.getPlanUsedCount())
                .planTotalDate(this.getPlanTotalDate())
                .planType(this.getPlanType())
                .build();

        return planDto;
    }
    public void 조회햇음() {
        this.planViews = this.planViews +1;
    }
}