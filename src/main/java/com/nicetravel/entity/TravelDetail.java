package com.nicetravel.entity;

import javax.persistence.*;

@Table(name = "traveldetail", indexes = {
        @Index(name = "travelId", columnList = "travelId")
})
@Entity
public class TravelDetail {
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