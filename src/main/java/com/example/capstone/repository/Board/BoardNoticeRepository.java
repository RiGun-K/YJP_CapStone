package com.example.capstone.repository.Board;

import com.example.capstone.domain.Board.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardNoticeRepository extends JpaRepository<Service, Long> {
}
