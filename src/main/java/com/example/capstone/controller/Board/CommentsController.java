package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.Comments;
import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.dto.Board.CommentDTO;
import com.example.capstone.repository.Board.BoardRepository;
import com.example.capstone.repository.Board.CommentsRepository;
import com.example.capstone.repository.Member.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("/api")
public class CommentsController {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    //    @GetMapping("/commentsList")
//    public List<Comments> commentList() {
//        List<Comments> commentList = commentsRepository.findAll();
//        System.out.println(commentList);
//        return commentList;
//    }

    @PostMapping("/comment")
    public Writer addComments(@RequestBody BoardDTO boardDTO) {
        System.out.println(boardDTO.getWriter_code());
        Optional<Writer> writers = boardRepository.findById(boardDTO.getWriter_code());
        System.out.println(writers.get().getWriter_code());

        Optional<Member> member = memberRepository.findByMID(boardDTO.getMid());
        System.out.println("------------------111-------------");
        System.out.println(boardDTO.getParentWriter());

        Optional<Writer> parWriter = boardRepository.findById(boardDTO.getParentWriter());
        Writer writer =  new Writer(boardDTO.getContent(), member.get(), parWriter.get());
        boardRepository.save(writer);
        return writer;

    }
}
