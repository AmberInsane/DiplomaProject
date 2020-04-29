package com.tms.stankevich.domain.movie;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Column(length = 1000)
    private String description;

    public boolean isNew() {
        return (this.id == null);
    }
}
