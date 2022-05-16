package com.example.capstone.dto.plan;


import com.example.capstone.domain.Member.Member;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long mCodeDto;
    private String mName;


    public MemberDto(Member member){
        this.mCodeDto = member.getMCode();
        this.mName = member.getMname();
    }

    @Builder
    public MemberDto(Long mCodeDto, String mName) {
        this.mCodeDto = mCodeDto;
        this.mName = mName;
    }
}
