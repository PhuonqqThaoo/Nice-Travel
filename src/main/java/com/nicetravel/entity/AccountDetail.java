package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
}