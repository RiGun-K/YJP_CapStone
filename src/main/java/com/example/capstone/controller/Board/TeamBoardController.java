package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.TeamWriter;
import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.dto.Board.TeamboardDTO;
import com.example.capstone.repository.Board.TeamWriterRepository;
import com.example.capstone.repository.Member.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("/api")
public class TeamBoardController {

    @Autowired
    private TeamWriterRepository teamWriterRepository;
    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/Teamwriting")
    public TeamWriter addWriter(@RequestBody TeamboardDTO teamboardDTO) {
        System.out.println(teamboardDTO.getTeamMaster());
        Optional<Member> member = memberRepository.findByMID(teamboardDTO.getTeamMaster());
        TeamWriter Writer = new TeamWriter(teamboardDTO.getNoticetitle(), teamboardDTO.getNoticecontent(), member.get());
        teamWriterRepository.save(Writer);
        return Writer;
    }
    @GetMapping("/teamlist")
    public List<TeamWriter> writerMyList() {
        List<TeamWriter> writerList = teamWriterRepository.findAll();
        System.out.println(writerList);
        return writerList;
    }
}

