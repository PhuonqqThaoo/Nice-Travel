package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Table(name = "bookingDetail")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetail implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "travelId")
    private Travel travelId;

    @Column(name = "price", nullable = false, precision = 12, scale = 3)
    private BigDecimal price;
    
    @Column(name = "totalquantity")
    private Integer totalQuantity;
    
    @Column(name = "qtynl")
    private Integer qtyNl;
    
    @Column(name = "qtyte")
    private Integer qtyTe;
    
    @Column(name = "qtytn")
    private Integer qtyTn;
    
    @Column(name = "qtyeb")
    private Integer qtyEb;
    
}