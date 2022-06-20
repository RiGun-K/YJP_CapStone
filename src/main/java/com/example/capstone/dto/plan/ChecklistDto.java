package com.example.capstone.dto.plan;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistDto {
    private Long checklistCode;
    private char checkState;
    private String checkContent;
    private Long detailCode;

}
