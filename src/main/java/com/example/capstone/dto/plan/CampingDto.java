package com.example.capstone.dto.plan;


import com.example.capstone.domain.Product.Camping;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CampingDto {

    private int campingId;
    private String campingName;
    private String address;
    private String campingDetailState;

    public CampingDto(Camping camping) {
        this.campingId = camping.getCampingId();
        this.campingName = camping.getCampingName();
        this.campingDetailState =camping.getCampingDetailState();
    this.address = camping.getAddress();
    }
}
