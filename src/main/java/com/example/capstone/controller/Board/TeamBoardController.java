package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.TeamWriter;
import com.example.capstone.repository.Board.TeamWriterRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@RequestMapping("/api")
public class TeamBoardController {

    @Autowired
    private TeamWriterRepository teamWriterRepository;

    @PostMapping("/Teamwriting")
    public 
}
