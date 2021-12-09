package com.nicetravel.entity;

import lombok.*;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.sql.Date;
import java.util.List;
import java.util.Locale;

@Table(name = "travel")
@Entity
@Getter
@Setter
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

    @ManyToOne( cascade = CascadeType.MERGE)
    @JoinColumn(name = "typeId", referencedColumnName = "id")
//    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private TravelTypes typeId;
    
    @JsonIgnore
    @OneToMany(mappedBy = "travelId")
    List<TravelDetail> travelDetails;
    
    @JsonIgnore
    @OneToMany(mappedBy = "travelId")
    List<TravelLike> travelLikes;
    
    @Column(name = "departurePlace", nullable = false, length = 225)
    private String departurePlace;

    @Column(name = "place", nullable = false, length = 225)
    private String place;

    @NumberFormat(style = NumberFormat.Style.CURRENCY, pattern = "#,###.###")
    @Column(name = "price", nullable = false, precision = 12, scale = 3)
    private BigDecimal price;

    @Column(name = "img", length = 225)
    private String img;

    @Transient
    public String getTravelPhotosImagePath() {
        if (img == null || name == null) {
            System.out.println("không có ảnh");
            return "/dashboard/img/logo/logoNiceTravel.png";
        }else if(img.equals("") || img.equals("logoNiceTravel.png")){
            System.out.println("ảnh không có giá trị");
            return "/dashboard/img/logo/logoNiceTravel.png";
        }

        System.out.println("/photos/travels/" + id + img);

        return "/photos/travels/" + id + "/" + img;
    }


    @Column(name = "createdDate", nullable = false)
    @CreationTimestamp // defaut getDate()
    private Date createdDate;

    @Column(name = "startDate", nullable = false)
    private Date startDate;

    @Column(name = "endDate", nullable = false)
    private Date endDate;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "hour", nullable = false)
    private Integer hour;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;
    
    @Column(name="quantityNew",nullable = false)
    private Integer quantityNew;

    @ManyToOne
    @JoinColumn(name = "account_Id")
    private Account travel_account_id;

    @Column(name = "expiration_date", nullable = false)
    private Boolean expirationDate = false;


}