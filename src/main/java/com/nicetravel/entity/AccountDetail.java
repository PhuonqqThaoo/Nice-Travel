package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Table(name = "account_detail")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAccount", nullable = false)
    private Integer id;

    @Column(name = "fullname", length = 225)
    private String fullname;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Column(name = "address", length = 225)
    private String address;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "img", length = 225)
    private String img;

    @Column(name = "idCard", nullable = false, length = 50)
    private String idCard;

    @Column(name = "createdDate", nullable = false)
    @CreationTimestamp // defaut getDate()
    private Timestamp createdDate;
}