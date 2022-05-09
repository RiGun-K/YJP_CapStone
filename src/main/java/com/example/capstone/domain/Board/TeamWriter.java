package com.example.capstone.domain.Board;

import com.example.capstone.domain.Plan.Team;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name="teamwriter")
public class TeamWriter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int teamwriter_code;

    @JoinColumn(name = "TEAMCODE")
    private Long teamCode;

//    @ManyToOne()
//    @JoinColumn(name = "TEAMMASTER")
//    private  Team teamMaster;

    @Column()
    private String teamMaster;
    private String noticetitle;
    private String noticecontent;

    public TeamWriter() {}
    public TeamWriter(String noticetitle, String noticecontent, String teamMaster){
        this.noticetitle = noticetitle;
        this.noticecontent = noticecontent;
        this.teamMaster = teamMaster;
    }

    public int getTeamwriter_code() {
        return teamwriter_code;
    }

    public void setTeamwriter_code(int teamwriter_code) {
        this.teamwriter_code = teamwriter_code;
    }

    public String getTeamMaster() {
        return teamMaster;
    }

    public void setTeamMaster(String teamMaster) {
        this.teamMaster = teamMaster;
    }

    public String getNoticetitle() {
        return noticetitle;
    }

    public void setNoticetitle(String noticetitle) {
        this.noticetitle = noticetitle;
    }

    public String getNoticecontent() {
        return noticecontent;
    }

    public void setNoticecontent(String noticecontent) {
        this.noticecontent = noticecontent;
    }
}
