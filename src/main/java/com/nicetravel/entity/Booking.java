package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Table(name = "booking")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account accountId;

    @Column(name = "createdDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @Column(name = "address", length = 225)
    private String address;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "totalPrice", nullable = false, precision = 12, scale = 3)
    private BigDecimal totalPrice;

    @Column(name = "payBoolean", nullable = false)
    private Boolean payBoolean = false;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

}