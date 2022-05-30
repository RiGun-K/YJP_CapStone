
package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.repository.Board.BoardRepository;
import com.example.capstone.repository.Member.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
public class BoardController {


    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

    /*게시글 작성*/
    @PostMapping("/Writing")
    public Board addWriter(@RequestParam(value = "file", required = false) MultipartFile uploadFile, BoardDTO boardDTO) throws IllegalStateException, IOException {

        try {
            String origFilename = uploadFile.getOriginalFilename();
            String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());

            String filename = now + "_" + origFilename;

            String savePath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets";

            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            boardDTO.setOrigFilename(origFilename);
            boardDTO.setFilename(filename);
            boardDTO.setFilePath(filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (boardDTO.getSavedTime() == null)
            boardDTO.setSavedTime(LocalDate.now().toString());

        Optional<Member> member = memberRepository.findByMID(boardDTO.getMid());
        System.out.println(member.get());


        Board board = new Board(boardDTO.getTitle(), boardDTO.getContent(), boardDTO.getOrigFilename(), boardDTO.getFilePath(), boardDTO.getFilename(), boardDTO.getSavedTime(), member.get());
        boardRepository.save(board);
        return board;


    }

    /* 게시글 조회 ( 댓글 없는 것만 ) */
    @GetMapping("/boardList")
    public List<Board> writerMyList() {
        List<Board> writerList = boardRepository.findByAllList();
        System.out.println(writerList);
        return writerList;
    }

    /* 게시글 상세페이지 조회 */
    @GetMapping("/myList/{boardId}")
    public Board myList(@PathVariable("boardId") Long boardId) {
        System.out.println("Vue에서 받은 데이터는" + boardId + " 입니다.");
        Optional<Board> myMyList = boardRepository.findById(boardId);
        return myMyList.get();
    }

    /* 게시글 댓글 조회 */
    @GetMapping("/myCommentList/{boardId}")
    public List<Board> commentList(@PathVariable("boardId") Long boardId) {
        List<Board> myMyList = boardRepository.findByCommentId(boardId);
        return myMyList;
    }

    /* 게시글 댓글 등록 */
    @PostMapping("/addComment")
    public void addComment(@RequestBody BoardDTO boardDTO) {
        System.out.println("==============================1===========================");
        System.out.println(boardDTO.getMid());
        System.out.println(boardDTO.getContent());
        System.out.println(boardDTO.getParentBoard());
        System.out.println("===============================================2===========================");

        Optional<Board> board1 = boardRepository.findById(boardDTO.getParentBoard());

        System.out.println(boardDTO.getMid());
        Optional<Member> member = memberRepository.findByMID(boardDTO.getMid());

        System.out.println(board1.get());
        System.out.println(member.get());

    }

    /* 게시글 댓글 삭제*/
    @DeleteMapping("/commentdelete/{boardId}")
    public String commentdeleteList(@PathVariable("boardId") Long boardId) {
        System.out.println("삭제할 댓글 번호는 : " + boardId);
        Optional<Board> board1 = boardRepository.findById(boardId);
        boardRepository.delete(board1.get());
        return "댓글이 삭제되었습니다.";
    }


    /* 게시글 삭제*/
    @DeleteMapping("/deleteList/{writer_code}")
    public String deleteList(@PathVariable("writer_code") Long writer_code) {
        System.out.println("삭제할 게시글 번호는 : " + writer_code);
        Optional<Board> writer = boardRepository.findById(writer_code);
        boardRepository.delete(writer.get());
        return "게시글이 삭제되었습니다.";


    }
}

    /* 게시글 수정*/
//    @PutMapping("/update")
//    public String updateList(@RequestBody BoardDTO boardDTO) {
//        Optional<Board> updateMyList = boardRepository.findById(boardDTO.getParentBoard());
//        updateMyList.get().setTitle(boardDTO.getTitle());
//        updateMyList.get().setContent(boardDTO.getContent());
//        boardRepository.save(updateMyList.get());
//        return "게시글이 수정되었습니다.";
//
//    }

//    @PutMapping("/update")
//    public Board addWriter(@RequestParam(value = "file", required = false) MultipartFile uploadFile, BoardDTO boardDTO, BindingResult result) throws IllegalStateException, IOException {
//
//        try {
//            String origFilename = uploadFile.getOriginalFilename();
//            String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
//
//            String filename = now + "_" + origFilename;
//
//            /* 실행되는 위치의 'files' 폴더에 파일이 저장 */
//            String savePath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets";
//            /* 파일이 저장되는 폴더가 없으면 폴더 생성 */
//            if (!new File(savePath).exists()) {
//                try {
//                    new File(savePath).mkdir();
//                } catch (Exception e) {
//                    e.getStackTrace();
//                }
//            }
//            String filePath = savePath + "\\" + filename;
//            uploadFile.transferTo(new File(filePath));
//
//            boardDTO.setOrigFilename(origFilename);
//            boardDTO.setFilename(filename);
//            boardDTO.setFilePath(filePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Optional<Board> updateMyList = boardRepository.findById(boardDTO.getParentBoard());
//
//    }