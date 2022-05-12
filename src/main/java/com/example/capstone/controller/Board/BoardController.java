package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.repository.Board.BoardRepository;
import com.example.capstone.repository.Member.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("/api")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;


    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/Writing")
    public Writer addWriter(@RequestBody BoardDTO boardDTO) {
        System.out.println(boardDTO.getMid());
        Optional<Member> member = memberRepository.findByMID(boardDTO.getMid());
        Writer writer = new Writer(boardDTO.getTitle(), boardDTO.getContent(), member.get());
        boardRepository.save(writer);
        return writer;
    }

    @GetMapping("/list")
    public List<Writer> writerMyList() {
        List<Writer> writerList = boardRepository.findAll();
        System.out.println(writerList);
        return writerList;
    }

    @GetMapping("/myList/{writer_code}")
    public Writer myList(@PathVariable("writer_code") int writer_code) {
        System.out.println("Vue에서 받은 데이터는" + writer_code + " 입니다.");

        Optional<Writer> myMyList = boardRepository.findById(writer_code);
        return myMyList.get();
    }

    @DeleteMapping("/deleteList/{writer_code}")
    public String deleteList(@PathVariable("writer_code") int writer_code) {
        System.out.println("삭제할 게시글 번호는 : " +writer_code);
        Optional<Writer> writer = boardRepository.findById(writer_code);
        boardRepository.delete(writer.get());
        return "게시글이 삭제되었습니다.";


    }

    @PutMapping("/update")
    public String updateList(@RequestBody Writer writer) {
        Optional<Writer> updateMyList = boardRepository.findById(writer.getWriter_code());
        updateMyList.get().setTitle(writer.getTitle());
        updateMyList.get().setContent(writer.getContent());

        boardRepository.save(updateMyList.get());
        return "게시글이 수정되었습니다.";

    }
}
