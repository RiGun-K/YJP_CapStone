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

    @Autowired
    private TeamRepository teamRepository;

    @PostMapping("/teamwriting")
    // 3. 받은 data 를 @RequestBody 를 사용해서 TeamboardDTO 로 담는다.
    public TeamWriter addWriter(@RequestBody TeamboardDTO teamboardDTO) {
        System.out.println(teamboardDTO.getTeamCode());
        // teammaster 찾기
        // Optional<Member> member = memberRepository.findByMID(teamboardDTO.getMid());

        // 4. teamMaster는 외래키이므로 teamMaster가 있는 team 테이블에서 찾는다.
        Optional<Team> teamCode = teamRepository.findByTeamCode(teamboardDTO.getTeamCode());
//        System.out.println(teamMaster.get().getTeamMaster());

        // 5. TeamWriter 생성자에 teamboardDTO에 담긴 데이터들을 담는다(보낸다).
        TeamWriter teamWriter = new TeamWriter(teamboardDTO.getNoticetitle(), teamboardDTO.getNoticecontent(), teamboardDTO.getTeamCode());
        // 6. 담긴 data들을 DB에 저장한다.
        teamWriterRepository.save(teamWriter);
        return teamWriter;
    }

    @GetMapping("/teamlist")
    public List<TeamWriter> teamwriterMyList() {
        List<TeamWriter> teamWriterList = teamWriterRepository.findAll();
        System.out.println(teamWriterList);
        return teamWriterList;
    }


    @GetMapping("/teammyList/{teamwriter_code}")
    public TeamWriter teammyList(@PathVariable("teamwriter_code") int teamwriter_code) {
        System.out.println("Vue에서 받은 데이터는" + teamwriter_code + " 입니다.");
// Optional<Team> teamMaster = teamRepository.findByTeamMaster(teamboardDTO.getTeamMaster());
        Optional<TeamWriter> teammyMyList = teamWriterRepository.findById(teamwriter_code);
        return teammyMyList.get();
    }


    @DeleteMapping("/teamdeleteList/{teamwriter_code}")
    public String teamdeleteList(@PathVariable("teamwriter_code") int teamwriter_code) {
        System.out.println("삭제할 게시글 번호는 : " + teamwriter_code);
        Optional<TeamWriter> teamWriter = teamWriterRepository.findById(teamwriter_code);
        teamWriterRepository.delete(teamWriter.get());
        return "게시글이 삭제되었습니다.";

    }

    @PutMapping("/tbupdate")
    public String teamupdateList(@RequestBody TeamWriter teamWriter) {
        Optional<TeamWriter> teamupdateMyList = teamWriterRepository.findById(teamWriter.getTeamwriter_code());
        teamupdateMyList.get().setNoticetitle(teamWriter.getNoticetitle());
        teamupdateMyList.get().setNoticecontent(teamWriter.getNoticecontent());

        teamWriterRepository.save(teamupdateMyList.get());
        return "게시글이 수정되었습니다.";
    }
}

