package com.example.capstone.domain.Plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class PlanDetail {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long detailCode;

   @JsonManagedReference
   // @JsonIgnore
    @OneToMany(mappedBy = "detailCode",cascade = {CascadeType.ALL})
    private List<Checklist> checklists = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_CODE")
    private Plan planCode;

    @Column
    private  String detailName;
    @Column
    private String detailMemo;
    @Column
    private String detailStart;
    @Column
    private String detailEnd;
    @Column
    private int detailDay;

    @Builder
    public PlanDetail(Long detailCode, List<Checklist> checklists, Plan planCode, String detailName, String detailMemo, String detailStart, String detailEnd, int detailDay) {
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