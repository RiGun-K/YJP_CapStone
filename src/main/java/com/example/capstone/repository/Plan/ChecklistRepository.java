package com.example.capstone.repository.Plan;


import com.example.capstone.domain.Plan.Checklist;
import com.example.capstone.domain.Plan.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {


    List<Checklist> findByDetailCode(PlanDetail planDetail);

}
