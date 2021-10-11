package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "age_type")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgeType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description", nullable = false, length = 225)
    private String description;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;
}