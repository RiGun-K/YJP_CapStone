package com.example.capstone.dto.plan;

import com.example.capstone.domain.Plan.Checklist;
import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.PlanDetail;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanDetailDto {

    private Long detailCode;

    private List<Checklist> checklists = new ArrayList<Checklist>();

    private PlanDto planCode;
    private String detailName;
    private String detailMemo;
    private String detailStart;
    private String detailEnd;
    private int detailDay;


    @Builder
    public PlanDetailDto(Long detailCode, List<Checklist> checklists, PlanDto planCode, String detailName, String detailMemo, String detailStart, String detailEnd, int detailDay) {
        this.detailCode = detailCode;
        this.checklists = checklists;
        this.planCode = planCode;
        this.detailName = detailName;
        this.detailMemo = detailMemo;
        this.detailStart = detailStart;
        this.detailEnd = detailEnd;
        this.detailDay = detailDay;
    }
}
