package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.BoardCamping;
import com.example.capstone.domain.Board.BoardMenu;
import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.dto.Board.CampingBoardDTO;
import com.example.capstone.repository.Board.BoardCampingRepository;
import com.example.capstone.repository.Board.BoardMenuRepository;
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
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@NoArgsConstructor
@RequestMapping("/api")
public class CampingBoardController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardCampingRepository boardCampingRepository;
    @Autowired
    BoardMenuRepository boardMenuRepository;

    @PostMapping("/CampingBoard")
    public BoardCamping addWriter(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingBoardDTO campingBoardDTO) throws IllegalStateException, IOException {

        try {
            String origFilename = uploadFile.getOriginalFilename();
            String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());

            String filename = now + "_" + origFilename;

            /* 실행되는 위치의 'files' 폴더에 파일이 저장 */
            String savePath = System.getProperty("user.dir") + "\\src\\frontend\\src\\assets";
            /* 파일이 저장되는 폴더가 없으면 폴더 생성 */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                }
                catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            uploadFile.transferTo(new File(filePath));

            campingBoardDTO.setOrigFilename(origFilename);
            campingBoardDTO.setFilename(filename);
            campingBoardDTO.setFilePath(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<Member> member = memberRepository.findByMID(campingBoardDTO.getMcode());
        System.out.println(member.get());

        Optional<BoardMenu> boardMenu = boardMenuRepository.findById(campingBoardDTO.getBoardMenuCode());
        System.out.println(boardMenu.get());

        if(campingBoardDTO.getSavedTime()==null)
            campingBoardDTO.setSavedTime(LocalDate.now().toString());

        BoardCamping boardCamping = new BoardCamping(campingBoardDTO.getCampingTitle(), campingBoardDTO.getCampingContent(), member.get(), boardMenu.get(), campingBoardDTO.getSavedTime(), campingBoardDTO.getOrigFilename(), campingBoardDTO.getOrigFilename(), campingBoardDTO.getFilePath());
        boardCampingRepository.save(boardCamping);
        return boardCamping;


//        System.out.println(boardCamping.getMcode());
//        System.out.println(boardCamping.getBoardMenuCode());

    }


        @GetMapping("/CampingBoardlist/{campingId}")
        public List<BoardCamping> boardCampingList(@PathVariable("campingId") int campingId) {
            System.out.println(campingId);
            List<BoardCamping> boardCampingList = boardCampingRepository.findBycampingId(campingId);
            System.out.println(boardCampingList);
            return boardCampingList;
        }

}
