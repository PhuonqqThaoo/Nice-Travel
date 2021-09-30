package com.nicetravel.entity;

import javax.persistence.*;

@Table(name = "payment", indexes = {
        @Index(name = "bookingId", columnList = "bookingId")
})
@Entity
public class Payment {
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