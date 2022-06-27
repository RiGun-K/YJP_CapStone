package com.example.capstone.domain.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Kind;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MEMEQUIPMENT")
@Getter
@Setter
public class MemberEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memEquipmentCode;

    @ManyToOne
    @JoinColumn(name = "MCode")
    private Member MCode;

    @Column
    private String memEquipmentName;

    @Column
    private String memEquipmentState;

    @Column
    private int memEquipmentCount;

    @ManyToOne
    @JoinColumn(name = "kindid")
    private Kind kindid;

    @ManyToOne
    @JoinColumn(name = "boxItemCode")
    @JsonBackReference
    private BoxItem boxItemCode;

    public MemberEquipment() {
    }


}


