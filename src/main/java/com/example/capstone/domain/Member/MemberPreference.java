package com.example.capstone.domain.Member;

import com.example.capstone.domain.Product.Kind;

import javax.persistence.*;

@Entity
@Table(name = "MemberPreference")
public class MemberPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long MPRC;

    @ManyToOne
    @JoinColumn(name = "MPRM")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "MPRP")
    private Kind kind;

    public MemberPreference(){

    }

    public MemberPreference(long MPRC, Member member, Kind kind) {
        this.MPRC = MPRC;
        this.member = member;
        this.kind = kind;
    }

    public long getMPRC() {
        return MPRC;
    }

    public void setMPRC(long MPRC) {
        this.MPRC = MPRC;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

}
