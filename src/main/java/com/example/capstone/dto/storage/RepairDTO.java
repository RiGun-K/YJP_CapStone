package com.example.capstone.dto.storage;

import com.example.capstone.domain.Product.MenuBuy;
import com.example.capstone.domain.storage.MemberEquipment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RepairDTO {
    private long memEquipmentCode;
    private int buyId;
}