package com.example.capstone.dto.plan;

import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.Team;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
public class PlanDto {

    private Long planCode;
    private TeamDto teamCode;
    private String planDestination;
    private String planName;
    private int planBudget;
    private String planType;
    private String planStart;
    private String planEnd;
    private String planOpen;
    private int planNumber;
    private int planTotalDate;
    private Integer planViews = 0;
    private Integer planUsedCount = 0;
    private String address;
    private String detailAddress;
    private String campingName;

    @Builder
    public PlanDto( String address, String detailAddress, String campingName, Long planCode, TeamDto teamCode, String planDestination, String planName, int planBudget, String planType, String planStart, String planEnd, String planOpen, int planNumber, int planTotalDate, Integer planViews, Integer planUsedCount) {
        this.planCode = planCode;
        this.teamCode = teamCode;
        this.planDestination = planDestination;
        this.planName = planName;
        this.planBudget = planBudget;
        this.planType = planType;
        this.planStart = planStart;
        this.planEnd = planEnd;
        this.planOpen = planOpen;
        this.planNumber = planNumber;
        this.planTotalDate = planTotalDate;
        this.planViews = planViews;
        this.planUsedCount = planUsedCount;
        this.detailAddress = detailAddress;
        this.campingName = campingName;
        this.address = address;
    }

    public PlanDto(Plan plan) {
        Team teamEntity = plan.getTeamCode();
        TeamDto teamDto = TeamDto.builder()
                .teamCode(teamEntity.getTeamCode())
                .teamName(teamEntity.getTeamName())
                .teamState(teamEntity.getTeamState())
                .teamMaster(teamEntity.getTeamMaster()).build();
        this.planCode = plan.getPlanCode();
        this.teamCode = teamDto;
        this.planDestination = plan.getPlanDestination();
        this.planName = plan.getPlanName();
        this.planBudget = plan.getPlanBudget();
        this.planType = plan.getPlanType();
        this.planStart = plan.getPlanStart();
        this.planEnd = plan.getPlanEnd();
        this.planOpen = plan.getPlanOpen();
        this.planNumber = plan.getPlanNumber();
        this.planTotalDate = plan.getPlanTotalDate();
        this.planViews = plan.getPlanViews();
        this.planUsedCount = plan.getPlanUsedCount();
        this.detailAddress = plan.getDetailAddress();
        this.campingName = plan.getCampingName();
        this.address = plan.getAddress();
    }


}
