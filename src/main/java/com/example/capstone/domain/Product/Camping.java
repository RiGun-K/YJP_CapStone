package com.example.capstone.domain.Product;

import javax.persistence.*;

@Entity
@Table(name = "camping")
public class Camping {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int menuid;

}
