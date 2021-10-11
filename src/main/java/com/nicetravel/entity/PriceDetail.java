package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "price_detail")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDetail implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "price", nullable = false, precision = 12, scale = 3)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "travelId")
    private Travel travelId;

    @ManyToOne
    @JoinColumn(name = "ageId")
    private AgeType ageId;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;


}