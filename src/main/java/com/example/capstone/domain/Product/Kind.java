package com.example.capstone.domain.Product;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="kind")
public class Kind {

    @Id
    private int kindid;

    @Column()
    private String kindname;

    //부모
    @ManyToOne
    @JoinColumn(name = "parentkindid")

    private Kind parentkind;

    //자식
    @OneToMany(mappedBy = "parentkind")



    public Kind() {}

    public Kind(String kindname) {
        this.kindname = kindname;
    }

    public Kind(String kindname, Kind parentkind) {
        this.kindname = kindname;
        this.parentkind = parentkind;
    }

    public Kind(int kindid, String kindname) {
        this.kindid = kindid;
        this.kindname = kindname;
    }


    public int getKindid() {
        return kindid;
    }

    public void setKindid(int kindid) {
        this.kindid = kindid;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public Kind getParentkind() {
        return parentkind;
    }

    public void setParentkind(Kind parentkind) {
        this.parentkind = parentkind;
    }

    public List<Kind> getChildrenkinds() {
        return childrenkinds;
    }

    public void setChildrenkinds(List<Kind> childrenkinds) {
        this.childrenkinds = childrenkinds;
    }

}
