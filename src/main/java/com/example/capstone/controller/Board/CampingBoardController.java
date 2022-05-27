package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.BoardCamping;
import com.example.capstone.domain.Board.BoardMenu;
import com.example.capstone.domain.Board.Writer;
import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Camping;
import com.example.capstone.dto.Board.BoardDTO;
import com.example.capstone.dto.Board.CampingBoardDTO;
import com.example.capstone.repository.Board.BoardCampingRepository;
import com.example.capstone.repository.Board.BoardMenuRepository;
import com.example.capstone.repository.Member.MemberRepository;
import com.example.capstone.repository.Product.CampingRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
    @Autowired
    CampingRepository campingRepository;

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

        Optional<Camping> camping = campingRepository.findById(campingBoardDTO.getCampingId());
        System.out.println(camping.get());

        if(campingBoardDTO.getSavedTime()==null)
            campingBoardDTO.setSavedTime(LocalDate.now().toString());

        BoardCamping boardCamping = new BoardCamping(campingBoardDTO.getCampingTitle(), campingBoardDTO.getCampingContent(), member.get(), boardMenu.get(), camping.get(), campingBoardDTO.getSavedTime(), campingBoardDTO.getOrigFilename(), campingBoardDTO.getFilename(), campingBoardDTO.getFilePath());
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



    @PutMapping("/CampingBoardupdate")
    public BoardCamping updateWriter(@RequestParam(value = "file", required = false) MultipartFile uploadFile, CampingBoardDTO campingBoardDTO, BindingResult result) throws IllegalStateException, IOException {

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

        System.out.println("=========1=============");
        Optional<Member> member = memberRepository.findByMID(campingBoardDTO.getMcode());
        Optional<Member> member1 = memberRepository.findByMCode(member.get().getMCode());
        System.out.println("=========2=============");


        System.out.println("=========3=============");
        Optional<BoardMenu> boardMenu = boardMenuRepository.findById(campingBoardDTO.getBoardMenuCode());
        System.out.println(boardMenu.get());

        Optional<Camping> camping = campingRepository.findById(campingBoardDTO.getCampingId());
        System.out.println(camping.get());

        campingBoardDTO.setSavedTime(LocalDate.now().toString());

        BoardCamping boardCamping = new BoardCamping(campingBoardDTO.getBoardCampingCode(), campingBoardDTO.getCampingTitle(), campingBoardDTO.getCampingContent(), member.get(), boardMenu.get(), camping.get(), campingBoardDTO.getSavedTime(), campingBoardDTO.getOrigFilename(), campingBoardDTO.getFilename(), campingBoardDTO.getFilePath(), campingBoardDTO.getRecommend());
        boardCampingRepository.save(boardCamping);
        return boardCamping;

    }

    @DeleteMapping("/CampingBoardDelete/{boardCampingCode}")
    public String boarddeleteList(@PathVariable("boardCampingCode") Long boardCampingCode) {
        System.out.println("삭제할 리뷰 번호는 : " +boardCampingCode);
        boardCampingRepository.deleteById(boardCampingCode);
        return "리뷰가 삭제되었습니다.";

    }



    @PostMapping("/Reviews_countView")
    public void countCampingView(@RequestBody HashMap<String, String> recommend){
        System.out.println(recommend.get("a"));
        Optional<BoardCamping> boardCamping = boardCampingRepository.findById((long) Integer.parseInt(recommend.get("a")));
        boardCamping.get().setRecommend(boardCamping.get().getRecommend()+1);
        boardCampingRepository.save(boardCamping.get());
    }


}
