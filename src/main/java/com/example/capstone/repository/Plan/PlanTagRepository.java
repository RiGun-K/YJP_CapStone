package com.example.capstone.repository.Plan;

import com.example.capstone.domain.Plan.PlanTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlanTagRepository extends JpaRepository<PlanTag,Long> {


//    List<PlanTag> findAllByTagContentContains(String tagContent);

    @Query("select p from PlanTag p where p.tagContent LIKE %:tagContent%")
    List<PlanTag> findAllByTagContentContains(@Param("tagContent") String tagContent);

    @Query("select p from PlanTag p left join p.plan pl where p.tagContent = :tagContent")
    List<PlanTag> findAllContent(@Param("tagContent") String tagContent);
}
