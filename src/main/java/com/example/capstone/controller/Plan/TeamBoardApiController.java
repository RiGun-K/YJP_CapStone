package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.TeamBoard;
import com.example.capstone.dto.plan.TeamBoardDto;
import com.example.capstone.service.TeamBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TeamBoardApiController {

    private final TeamBoardService teamBoardService;


    @PostMapping("api/insertContent")
    public List<TeamBoardDto> insertContent(@RequestBody TeamBoardDto teamBoardDto){
        System.out.println(teamBoardDto.toString());
   return  teamBoardService.insertContent(teamBoardDto);

    }
}
