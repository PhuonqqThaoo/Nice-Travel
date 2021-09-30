package com.nicetravel.entity;

import javax.persistence.*;

@Table(name = "accountdetail", indexes = {
        @Index(name = "idAccount", columnList = "idAccount")
})
@Entity
public class AccountDetail {
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