package com.example.capstone.controller.Plan;



import com.example.capstone.domain.Plan.Checklist;
import com.example.capstone.domain.Plan.Plan;
import com.example.capstone.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class ChecklistApiController {

    private final ChecklistService checklistService;

    @PutMapping("/api/insertChecklist")
    public void insertChecklist(@RequestBody Checklist checklist) {
        checklistService.insertChecklist(checklist);
    }

    @PostMapping("/api/loadChecklist")
    public List<Checklist> loadChecklist(@RequestBody Checklist checklist) {
        return checklistService.loadChecklist(checklist);
    }

    @PutMapping("/api/updateState")
    public Optional<Checklist> updateState(@RequestBody Checklist checklistCode) {
        return checklistService.updateState(checklistCode);
    }

    @DeleteMapping("/api/deleteChecklist")
    public void deleteChecklist(@RequestBody Checklist checklist) {
        checklistService.deleteChecklist(checklist);
    }
    @GetMapping("api/loadAllCheckList")
    public List<Checklist> loadAllCheckList(@RequestParam Long planCode){
        return checklistService.loadAllCheckList(planCode);
    }
}
