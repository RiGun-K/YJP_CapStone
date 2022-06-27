package com.example.capstone.domain.storage;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOXITEM")
@Getter
@Setter
public class BoxItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long boxItemCode;

    @Column
    private int boxItemCount;

    @Column
    private String boxItemState;

    @ManyToOne
    @JoinColumn(name = "memEquipmentCode")
    private MemberEquipment memEquipmentCode;

    @ManyToOne
    @JoinColumn(name = "useStorageBoxCode")
    @JsonBackReference
    private UseStorageBox useStorageBoxCode;

    public BoxItem() {
    }
}
