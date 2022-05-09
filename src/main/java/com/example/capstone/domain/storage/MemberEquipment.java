package com.example.capstone.domain.storage;

import com.example.capstone.domain.Member.Member;
import com.example.capstone.domain.Product.Kind;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Columns;

import javax.persistence.*;

@Entity
@Table(name = "MEMEQUIPMENT")
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

    public MemberEquipment() {
    }

    public long getMemEquipmentCode() {
        return memEquipmentCode;
    }

    public void setMemEquipmentCode(long memEquipmentCode) {
        this.memEquipmentCode = memEquipmentCode;
    }

    public Member getMCode() {
        return MCode;
    }

    public void setMCode(Member MCode) {
        this.MCode = MCode;
    }

    public String getMemEquipmentName() {
        return memEquipmentName;
    }

    public void setMemEquipmentName(String memEquipmentName) {
        this.memEquipmentName = memEquipmentName;
    }

    public String getMemEquipmentState() {
        return memEquipmentState;
    }

    public void setMemEquipmentState(String memEquipmentState) {
        this.memEquipmentState = memEquipmentState;
    }

    public int getMemEquipmentCount() {
        return memEquipmentCount;
    }

    public void setMemEquipmentCount(int memEquipmentCount) {
        this.memEquipmentCount = memEquipmentCount;
    }

    public Kind getKindid() {
        return kindid;
    }

    public void setKindid(Kind kindid) {
        this.kindid = kindid;
    }
}


