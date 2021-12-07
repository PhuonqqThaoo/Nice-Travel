package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "Events")

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "title" )
    String title;

    @Column(name = "description")
    String description;


}
