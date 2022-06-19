package com.example.capstone.domain.Board;

import com.example.capstone.domain.Member.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceId;

    @Column()
    private String title;
    private String content;

    @Column()
    private String origFilename;
    @Column()
    private String filename;
    @Column()
    private String filePath;

    @Column()
    private Integer boardViews = 0;
    @CreatedDate
    private String savedTime;

    @ManyToOne()
    @JoinColumn(name = "MCODE")
    private Member mcode;

    public Service(){

    }





}
