package com.example.capstone.dto.plan;

import com.example.capstone.domain.Plan.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CopyPlanDto {
    private PlanDto planDto;
    private PlanDto oldPlanDto;
    private Team teamDto;
private PlanTagDto planTagDto;
//
}
