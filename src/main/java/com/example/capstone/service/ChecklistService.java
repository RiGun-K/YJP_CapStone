package com.example.capstone.service;


import com.example.capstone.domain.Plan.Checklist;
import com.example.capstone.repository.Plan.ChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class ChecklistService {

    private final ChecklistRepository checklistRepository;

    public void insertChecklist(Checklist checklist) {
        checklistRepository.save(checklist);
    }

    public List<Checklist> loadChecklist(Checklist checklist) {
        List<Checklist> ch = checklistRepository.findByDetailCode(checklist.getDetailCode());
        return ch;
    }

    public Optional<Checklist> updateState(Checklist checklistCode) {
        System.out.println(checklistCode);
        Optional<Checklist> cl = checklistRepository.findById(checklistCode.getChecklistCode());
//        Checklist ch =  checklistRepository.findById(checklistCode.getChecklistCode()).get();
        Checklist ch = cl.get();
        System.out.println(cl.get());
        if (cl.get().getCheckState() == 'y') {
            cl.get().setCheckState('n');
            checklistRepository.save(cl.get());
        } else if (cl.get().getCheckState() == 'n') {
            cl.get().setCheckState('y');
            checklistRepository.save(cl.get());
        }
        return cl;


    }

    public void deleteChecklist(Checklist checklist) {
        checklistRepository.findById(checklist.getChecklistCode());
        checklistRepository.deleteById(checklist.getChecklistCode());
    }
}
