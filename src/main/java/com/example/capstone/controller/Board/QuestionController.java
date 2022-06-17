package com.example.capstone.controller.Board;


import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Board.Question;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.QuestionDTO;
import com.example.capstone.repository.Board.QuestionRepository;
import com.example.capstone.repository.Member.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MemberRepository memberRepository;

    /* 문의 작성 */
//    @PostMapping("/questionWriting")
//    public Question addWriter(@RequestParam(value = "file", required = false) MultipartFile uploadFile, QuestionDTO questionDTO) throws IllegalStateException, IOException {
//
//        try {
//            String origFilename = uploadFile.getOriginalFilename();
//            String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
//
//            String filename = now + "_" + origFilename;
//
//            String savePath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets";
//
//            if (!new File(savePath).exists()) {
//                try {
//                    new File(savePath).mkdir();
//
//                } catch (Exception e) {
//                    e.getStackTrace();
//                }
//            }
//            String filePath = savePath + "\\" + filename;
//            uploadFile.transferTo(new File(filePath));
//
//            questionDTO.setOrigFilename(origFilename);
//            questionDTO.setFilename(filename);
//            questionDTO.setFilePath(filePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (questionDTO.getSavedTime() == null)
//            questionDTO.setSavedTime(LocalDate.now().toString());
//
//        Optional<Member> member = memberRepository.findByMID(questionDTO.getMid());
//        System.out.println(member.get());
//
//        Question question = new Question(questionDTO.getTitle(), questionDTO.getContent(), questionDTO.getOrigFilename(), questionDTO.getFilePath(), questionDTO.getFilename(), questionDTO.getSavedTime(), member.get() );
//        questionRepository.save(question);
//        return question;
//
//    }

    @GetMapping("/QuestionList")
    public List<Question> questionMyList() {
        List<Question> questionList = questionRepository.findByAllList();
        System.out.println(questionList);
        return questionList;
    }

}
