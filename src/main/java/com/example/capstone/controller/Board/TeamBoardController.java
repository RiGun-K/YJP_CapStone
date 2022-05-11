package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.TeamWriter;
import com.example.capstone.domain.Plan.Team;
import com.example.capstone.dto.Board.TeamboardDTO;
import com.example.capstone.repository.Board.TeamWriterRepository;
import com.example.capstone.repository.Plan.TeamRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class TeamBoardController {

    @Autowired
    private TeamWriterRepository teamWriterRepository;


}

