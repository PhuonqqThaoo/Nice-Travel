package com.nicetravel.entity;

import javax.persistence.*;

@Table(name = "pricedetail", indexes = {
        @Index(name = "travelId", columnList = "travelId"),
        @Index(name = "ageId", columnList = "ageId")
})
@Entity
public class PriceDetail {
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