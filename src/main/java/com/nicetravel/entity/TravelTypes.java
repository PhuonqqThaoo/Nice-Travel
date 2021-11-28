package com.nicetravel.entity;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "travel_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelTypes implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

    @JsonIgnore
	@OneToMany(mappedBy = "typeId")
	List<Travel> travels;
}