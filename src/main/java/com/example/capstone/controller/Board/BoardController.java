package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Board.BoardCamping;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.repository.Board.BoardRepository;
import com.example.capstone.repository.Member.MemberRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

//    @PostMapping("/Writing")
//    public Board addWriter(@RequestBody BoardDTO boardDTO) {
//        System.out.println(boardDTO.getMid());
//        Optional<Member> member = memberRepository.findByMID(boardDTO.getMid());
//        Board writer = new Board(boardDTO.getTitle(), boardDTO.getContent(),boardDTO.getOrigFilename(), boardDTO.getFilePath(), member.get());
//        boardRepository.save(writer);
//        return writer;
//    }

//    @PostMapping("/Writing")
//    public Writer addWriter(@RequestParam(value = "file", required = false) MultipartFile uploadFile, BoardDTO boardDTO) throws IllegalStateException, IOException {
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
//                }
//                catch (Exception e) {
//                    e.getStackTrace();
//                }
//            }
//            String filePath = savePath + "\\" + filename;
//            uploadFile.transferTo(new File(filePath));
//
//            boardDTO.setOrigFilename(origFilename);
//            boardDTO.setFilePath(filePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Optional<Member> member = memberRepository.findByMID(boardDTO.getMid());
//        System.out.println(member.get());
//
//        Optional<>
//
//        Writer writer = new Writer(boardDTO.getTitle(), boardDTO.getContent(),boardDTO.getOrigFilename(), boardDTO.getFilePath(), member.get());
//        boardRepository.save(writer);
//        return writer;
//
//
//    }

    @GetMapping("/boardList")
    public List<Board> writerMyList() {
        List<Board> writerList = boardRepository.findAll();
        System.out.println(writerList);
        return writerList;
    }

    /* 내 게시글 상세페이지 조회 */
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
    @PostMapping("/addComment/{boardId}")
    public void addComment(@PathVariable("boardId") Long boardId, BoardDTO boardDTO) {
        Optional<Board> board1 = boardRepository.findById(boardId);

        System.out.println(boardDTO.getMCODE());
        Optional<Member> member = memberRepository.findByMID(boardDTO.getMCODE());

        System.out.println(board1.get());
        System.out.println(member.get());

    }

    @DeleteMapping("/deleteList/{writer_code}")
    public String deleteList(@PathVariable("writer_code") Long writer_code) {
        System.out.println("삭제할 게시글 번호는 : " +writer_code);
        Optional<Board> writer = boardRepository.findById(writer_code);
        boardRepository.delete(writer.get());
        return "게시글이 삭제되었습니다.";


    }

//    @PutMapping("/update")
//    public String updateList(@RequestBody Board board) {
//        Optional<Board> updateMyList = boardRepository.findById(board.getBoardId());
//        updateMyList.get().setTitle(board.getTitle());
//        updateMyList.get().setContent(board.getContent());
//        boardRepository.save(updateMyList.get());
//        return "게시글이 수정되었습니다.";
//
//    }


}
