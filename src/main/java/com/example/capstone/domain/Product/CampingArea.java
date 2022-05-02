package com.example.capstone.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="campingarea")
public class CampingArea {

    @Id
    private int areaId;

    @Column()
    private String areaName;

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
}
