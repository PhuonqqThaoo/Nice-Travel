package com.nicetravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Table(name = "booking")
@Entity
@Getter
@Setter
@ToString
public class Booking implements Serializable {

    public Booking(Integer id, List<BookingDetail> bookingDetails, Date createdDate, String address, String phone, BigDecimal totalPrice, Boolean payBoolean, Boolean isDeleted, String verification_code, Account booking_account_id) {
        this.id = id;
        this.bookingDetails = bookingDetails;
        this.createdDate = createdDate;
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.payBoolean = payBoolean;
        this.isDeleted = isDeleted;
        this.verification_code = verification_code;
        this.booking_account_id = booking_account_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "account_id")
//    private Account account_id;
    
    @JsonIgnore
    @OneToMany(mappedBy = "bookingId")
    @ToString.Exclude
    List<BookingDetail> bookingDetails;

    @Column(name = "createdDate", nullable = false)
    @CreationTimestamp // defaut getDate()
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public Booking() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking booking = (Booking) o;
        return id != null && Objects.equals(id, booking.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}