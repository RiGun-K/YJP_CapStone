package com.example.capstone.dto.plan;

import com.example.capstone.domain.Plan.Plan;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PlanTagDto {

    private Plan plan;
    private List<String> tagContentList;


}
