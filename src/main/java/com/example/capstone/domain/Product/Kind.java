package com.example.capstone.domain.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    private List<Kind> childrenkinds = new ArrayList<>();


    public Kind() {}
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
//    public List<Menu> getMenus() {
//        return menus;
//    }
//
//    public void setMenus(List<Menu> menus) {
//        this.menus = menus;
//    }
}
