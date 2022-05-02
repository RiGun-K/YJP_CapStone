package com.example.capstone.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "infoter")
public class Infoter {

    @Id
    private int infoterid;

    @Column()
    private String infotername;

    public Infoter() {}
    public Infoter(int infoterid, String infotername) {
        this.infoterid = infoterid;
        this.infotername = infotername;
    }

    public int getInfoterid() {
        return infoterid;
    }

    public void setInfoterid(int infoterid) {
        this.infoterid = infoterid;
    }

    public String getInfotername() {
        return infotername;
    }

    public void setInfotername(String infotername) {
        this.infotername = infotername;
    }
}
