package com.nicetravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Table(name = "Account", indexes = {
        @Index(name = "UQ__Account__AB6E6164B485A157", columnList = "email", unique = true),
        @Index(name = "UQ__Account__F3DBC57267F2E915", columnList = "username", unique = true)
})
@Entity
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    private static final long PASSWORD_EXPIRATION_TIME
            = 30L * 24L * 60L * 60L * 1000L;    // 30 days
    @Column(name = "password_changed_time")
    private Date passwordChangedTime;

    public boolean isPasswordExpired() {
        if (this.passwordChangedTime == null) return false;

        long currentTime = System.currentTimeMillis();
        long lastChangedTime = this.passwordChangedTime.getTime();

        return currentTime > lastChangedTime + PASSWORD_EXPIRATION_TIME;
    }

    @NotEmpty
    @NotNull
    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "fullname")
//    @NotEmpty
//	@NotNull
    @Size(max = 30)
    private String fullname;

    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "Không để trống email")
    // áp dụng cho chuỗi
    @Email(message = "Không đúng định dạng email")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Column(name = "address", length = 225)
    private String address;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Transient
    public String getPhotosImagePath() {
        if (img == null || username == null) {
            return "/dashboard/img/user.png";
        }else if(img.equals("") || img.equals("user.png")){
            return "/dashboard/img/user.png";
        }
        else if(provider == null || provider.equals(Provider.DATABASE) || provider.equals(Provider.FACEBOOK)){
            return "/photos/accounts/" + username + "/" + img;
        }
        else if(provider.equals(Provider.GOOGLE)){
            return img;
        }

        return "/photos/accounts/" + username + "/" + img;
    }

    @Column(name = "img", length = 225)
    private String img;

    @NotNull
    @Column(name = "id_card", nullable = false, length = 50)
    private String id_Card;

    @ManyToOne
    @JoinColumn(name = "role_Id")
    private Role role_Id;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable = false;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "travel_like_account_id")
    @ToString.Exclude
    List<TravelLike> travelLikes;

    @JsonIgnore
    @OneToMany(mappedBy = "travel_account_id",fetch = FetchType.EAGER)
    @ToString.Exclude
    List<Travel> travels;


}