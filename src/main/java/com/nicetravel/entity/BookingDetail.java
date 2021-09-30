package com.nicetravel.entity;

import javax.persistence.*;

@Table(name = "bookingdetail", indexes = {
        @Index(name = "travelId", columnList = "travelId"),
        @Index(name = "bookingId", columnList = "bookingId")
})
@Entity
public class BookingDetail {
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