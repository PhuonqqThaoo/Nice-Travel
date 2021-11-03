package com.nicetravel.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "travel_like")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelLike implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "travelId")
    private Travel travelId;
	
	@ManyToOne
    @JoinColumn(name = "accountlId")
    private Account accountId;
}
