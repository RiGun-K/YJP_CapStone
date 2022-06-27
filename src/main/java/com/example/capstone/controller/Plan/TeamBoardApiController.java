package com.example.capstone.controller.Plan;


import com.example.capstone.domain.Plan.TeamBoard;
import com.example.capstone.dto.plan.TeamBoardDto;
import com.example.capstone.dto.plan.TeamDto;
import com.example.capstone.service.TeamBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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
    public Page<TeamBoardDto> loadTeamBoards(@RequestParam Long teamCode, Pageable pageable){
        return teamBoardService.loadTeamBoards(teamCode,pageable);
    }

    @PutMapping("api/editContent")
    public void editContent(@RequestBody TeamBoardDto teamBoardDto){
        System.out.println(teamBoardDto.toString());
        teamBoardService.editContent(teamBoardDto);
    }
    @DeleteMapping("api/deleteContent")
    public void deleteContent(@RequestParam Long teamBoardDto){
        teamBoardService.deleteContent(teamBoardDto);
    }
}
