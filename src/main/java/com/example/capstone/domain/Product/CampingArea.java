package com.example.capstone.domain.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="campingarea")
public class CampingArea {

    @Id
    private int areaId;

    @Column()
    private String areaName;

    //부모
    @ManyToOne
    @JoinColumn(name = "parentareaid")
    @JsonManagedReference
    private CampingArea parentcampingarea;

    //자식
    @OneToMany(mappedBy = "parentcampingarea")
    @JsonBackReference
    private List<CampingArea> childrencampingarea = new ArrayList<>();



    public CampingArea() {}
    public CampingArea(int areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public CampingArea getParentcampingarea() {
        return parentcampingarea;
    }

    public void setParentcampingarea(CampingArea parentcampingarea) {
        this.parentcampingarea = parentcampingarea;
    }

    public List<CampingArea> getChildrencampingarea() {
        return childrencampingarea;
    }

    public void setChildrencampingarea(List<CampingArea> childrencampingarea) {
        this.childrencampingarea = childrencampingarea;
    }
}