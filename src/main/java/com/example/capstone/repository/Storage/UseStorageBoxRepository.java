package com.example.capstone.repository.Storage;

import com.example.capstone.domain.order.Orders;
import com.example.capstone.domain.storage.StorageBox;
import com.example.capstone.domain.storage.UseStorageBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UseStorageBoxRepository extends JpaRepository<UseStorageBox, Long> {
    List<UseStorageBox> findByStorageBoxCode(StorageBox storageBox);
    List<UseStorageBox> findByOrderCode(Orders orders);
}
