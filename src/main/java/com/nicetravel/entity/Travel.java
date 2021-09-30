package com.nicetravel.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "travel", indexes = {
        @Index(name = "typeId", columnList = "typeId")
})
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 225)
    private String name;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private TravelType typeId;

    @Column(name = "departurePlace", nullable = false, length = 225)
    private String departurePlace;

    @Column(name = "place", nullable = false, length = 225)
    private String place;

    @Column(name = "price", nullable = false, precision = 12, scale = 3)
    private BigDecimal price;

    @Column(name = "img", length = 225)
    private String img;

    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @Column(name = "startDate", nullable = false)
    private Instant startDate;

    @Column(name = "endDate", nullable = false)
    private Instant endDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "hour", nullable = false)
    private Integer hour;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public TravelType getTypeId() {
        return typeId;
    }

    public void setTypeId(TravelType typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}