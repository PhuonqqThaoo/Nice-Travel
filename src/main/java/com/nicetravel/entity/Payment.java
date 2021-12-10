package com.nicetravel.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

@Table(name = "payment")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking bookingId;

    @Column(name = "payTime", nullable = false)
    private Date payTime;

//    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###.###")
    @Column(name = "totalPrice", nullable = false, precision = 12, scale = 3)
    private BigDecimal totalPrice;
}