package com.example.capstone.domain.storage;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ASSORTITEM")
public class AssortItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long assortItemCode;

    @Column
    private String assortItemName;

    //부모
    @ManyToOne
    @JoinColumn(name = "assortItemParCode")
    @JsonManagedReference
    private AssortItem assortItemParCode;

    //자식
    @OneToMany(mappedBy = "assortItemParCode")
    @JsonBackReference
    private List<AssortItem> assortItemChiCode = new ArrayList<>();

    public AssortItem() {
    }

    public AssortItem(long assortItemCode, String assortItemName) {
        this.assortItemCode = assortItemCode;
        this.assortItemName = assortItemName;
    }

    public long getAssortItemCode() {
        return assortItemCode;
    }

    public void setAssortItemCode(long assortItemCode) {
        this.assortItemCode = assortItemCode;
    }

    public String getAssortItemName() {
        return assortItemName;
    }

    public void setAssortItemName(String assortItemName) {
        this.assortItemName = assortItemName;
    }

    public AssortItem getAssortItemParCode() {
        return assortItemParCode;
    }

    public void setAssortItemParCode(AssortItem assortItemParCode) {
        this.assortItemParCode = assortItemParCode;
    }

    public List<AssortItem> getAssortItemChiCode() {
        return assortItemChiCode;
    }

    public void setAssortItemChiCode(List<AssortItem> assortItemChiCode) {
        this.assortItemChiCode = assortItemChiCode;
    }
}
