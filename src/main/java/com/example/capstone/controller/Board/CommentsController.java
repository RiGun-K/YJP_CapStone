package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.Comments;
import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.CommentDTO;
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

    //    @GetMapping("/commentsList")
//    public List<Comments> commentList() {
//        List<Comments> commentList = commentsRepository.findAll();
//        System.out.println(commentList);
//        return commentList;
//    }
//    @PostMapping("/comment")
//    public Comments addWriter(@RequestBody CommentDTO commentDTO) {
//        System.out.println(commentDTO.getMID());
//        Optional<Member> member = memberRepository.findByMID(commentDTO.getMID());
//        Writer writer = new Writer(commentDTO.getCommenttext(), commentDTO.getCommentdate(), member.get());
//        commentsRepository.save()
//
//    }
}
