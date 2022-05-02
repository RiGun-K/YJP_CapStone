package com.example.capstone.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "infoter")
public class Infoter {

    @Id
    private int infoterId;

    @Column()
    private String infoterName;

    public Infoter() {}
    public Infoter(int infoterId, String infoterName) {
        this.infoterId = infoterId;
        this.infoterName = infoterName;
    }

    public int getInfoterId() {
        return infoterId;
    }

    public void setInfoterId(int infoterId) {
        this.infoterId = infoterId;
    }

    public String getInfoterName() {
        return infoterName;
    }

    public void setInfoterName(String infoterName) {
        this.infoterName = infoterName;
    }
}
