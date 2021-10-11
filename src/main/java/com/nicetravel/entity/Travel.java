package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Table(name = "travel")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Travel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 225)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private TravelTypes typeId;

    @Column(name = "departurePlace", nullable = false, length = 225)
    private String departurePlace;

    @Column(name = "place", nullable = false, length = 225)
    private String place;

    @Column(name = "price", nullable = false, precision = 12, scale = 3)
    private BigDecimal price;

    @Column(name = "img", length = 225)
    private String img;

    @Column(name = "createdDate", nullable = false)
    @CreationTimestamp // defaut getDate()
    private Timestamp createdDate;

    @Column(name = "startDate", nullable = false)
    private Timestamp startDate;

    @Column(name = "endDate", nullable = false)
    private Timestamp endDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "hour", nullable = false)
    private Integer hour;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

}