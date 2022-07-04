package com.example.capstone.dto.plan;

import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.domain.Plan.Team;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class PlanTagDto {

    //private Plan plan;
    private  String planName;
    private  Long teamCode;
    private  String planStart;
    private  String planEnd;
    private  String planDestination;
    private  String planType;
    private  int planNumber;
    private  int planBudget;
    private  int planTotalDate;
    private  String planOpen;
    private  String address;
    private  String detailAddress;
    private  String campingName;
    private List<String> tagContentList;
    private String origFilename;
    private String filename;
    private String filePath;





}
