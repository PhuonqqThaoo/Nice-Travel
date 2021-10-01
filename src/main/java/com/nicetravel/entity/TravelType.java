package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "travelTypes")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false, length = 225)
    private String type;

    @Column(name = "description", length = 225)
    private String description;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

}