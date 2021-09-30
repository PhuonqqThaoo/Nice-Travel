package com.nicetravel.entity;

import javax.persistence.*;

@Table(name = "traveltypes")
@Entity
public class TravelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}