package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

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
    private Instant payTime;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###.###")
    @Column(name = "totalPrice", nullable = false, precision = 12, scale = 3)
    private BigDecimal totalPrice;

    @Column(name = "tourName", length = 250)
    private String tourName;

    @Column(name = "subtotal")
    private Double subtotal;

    @Column(name = "shipping")
    private Double shipping;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "total")
    private Double total;

    public Payment(String tourName, String subtotal,
                       String shipping, String tax, String total) {
        this.tourName = tourName;
        this.subtotal = Double.parseDouble(subtotal);
        this.shipping = Double.parseDouble(shipping);
        this.tax = Double.parseDouble(tax);
        this.total = Double.parseDouble(total);
    }

    public String getTourName() {
        return tourName;
    }

    public String getSubtotal() {
        return String.format("%.2f", subtotal);
    }

    public String getShipping() {
        return String.format("%.2f", shipping);
    }

    public String getTax() {
        return String.format("%.2f", tax);
    }

    public String getTotal() {
        return String.format("%.2f", total);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public Instant getPayTime() {
        return payTime;
    }

    public void setPayTime(Instant payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}