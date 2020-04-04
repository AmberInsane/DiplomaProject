package com.tms.stankevich.domain.movie;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Hall implements Serializable {
    private static final long serialVersionUID = 42L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private Long id;

    @Column
    private String name;

    @Column
    private Short capacity;
}
