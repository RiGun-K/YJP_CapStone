package com.example.capstone.dto.plan;

import java.util.ArrayList;
import java.util.List;

public class ChecklistDto {


    private Long teamCode;

    private String teamName;

    private char teamState;


    private String teamMaster;

    private List<ChecklistDto> checkList = new ArrayList<>();



}
