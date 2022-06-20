package com.example.capstone.controller.Board;

import com.example.capstone.domain.Board.Board;
import com.example.capstone.domain.Board.Service;
import com.example.capstone.repository.Board.BoardNoticeRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ServiceController {

    @Autowired
    private BoardNoticeRepository boardNoticeRepository;

    /* 공지사항 조회 */
    @GetMapping("/NoticeList")
    public List<Service> noticeMyList(){
        List<Service> noticeList = boardNoticeRepository.findAll();
        System.out.println(noticeList);
        return noticeList;
    }

    /* 공지사항 조회수 */
    @PostMapping("/Notice_countView")
    public void countBoardView(@RequestBody HashMap<String, String> serviceViews){
        System.out.println(serviceViews.get("a"));
        Optional<Service> service = boardNoticeRepository.findById((long) Integer.parseInt(serviceViews.get("a")));
        service.get().setBoardViews(service.get().getBoardViews()+1);
        boardNoticeRepository.save(service.get());
    }

    /* 공지사항 상세페이지 조회 */
    @GetMapping("/noticemyList/{serviceId}")
    public Service myList(@PathVariable("serviceId") Long serviceId) {
        System.out.println("Vue에서 받은 데이터는" + serviceId + " 입니다.");
        Optional<Service> myMyList = boardNoticeRepository.findById(serviceId);
        return myMyList.get();
    }

}
