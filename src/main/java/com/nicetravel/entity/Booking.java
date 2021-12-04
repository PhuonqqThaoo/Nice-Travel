package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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

//    @ManyToOne
//    @JoinColumn(name = "account_id")
//    private Account account_id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "bookingId")
    List<BookingDetail> bookingDetails;

    @Column(name = "createdDate", nullable = false)
    @CreationTimestamp // defaut getDate()
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


    @Column(name = "verification_code", length = 64)
    private String verification_code;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account booking_account_id;

}