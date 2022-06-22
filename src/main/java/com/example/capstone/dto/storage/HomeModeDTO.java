package com.example.capstone.dto.storage;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeModeDTO {
    private long useBoxCode;
    private Member member;
}
