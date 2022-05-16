package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.TeamBoard;
import com.example.capstone.dto.plan.TeamBoardDto;
import com.example.capstone.dto.plan.TeamDto;
import com.example.capstone.service.TeamBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TeamBoardApiController {

    private final TeamBoardService teamBoardService;


    @PostMapping("api/insertContent")
    public void insertContent(@RequestBody TeamBoardDto teamBoardDto){
        System.out.println(teamBoardDto.toString());
     teamBoardService.insertContent(teamBoardDto);

    }
    @GetMapping("api/loadTeamBoards")
    public List<TeamBoardDto> loadTeamBoards(@RequestParam Long teamCode){
        return teamBoardService.loadTeamBoards(teamCode);
    }
}
