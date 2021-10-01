package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "payment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking bookingId;

    @Column(name = "payTime", nullable = false)
    private Instant payTime;

    @Column(name = "totalPrice", nullable = false, precision = 12, scale = 3)
    private BigDecimal totalPrice;

}