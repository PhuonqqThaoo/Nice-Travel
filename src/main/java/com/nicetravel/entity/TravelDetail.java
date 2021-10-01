package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "travelDetail")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"time\"", nullable = false, length = 50)
    private String time;

    @Column(name = "description", length = 225)
    private String description;

    @ManyToOne
    @JoinColumn(name = "travelId")
    private Travel travelId;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

}