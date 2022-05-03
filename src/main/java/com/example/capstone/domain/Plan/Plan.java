package com.example.capstone.domain.Plan;



import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity
 @ToString
public class Plan {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long planCode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name= "TEAMCODE")
    private Team teamCode;

    @Column
    private String planDestination;
    @Column
    private String planName;
    @Column
    private int planBudget;
    @Column
    private String planType;
    @Column
    private String planStart;
    @Column
    private String planEnd;
    @Column
    private String planOpen;
    @Column
    private int planNumber;
    @Column
    private int planTotalDate;
    @Column
    private Integer planViews=0;
    @Column
    private Integer planUsedCount=0;
}