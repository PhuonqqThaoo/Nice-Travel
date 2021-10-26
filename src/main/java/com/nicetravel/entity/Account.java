package com.nicetravel.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.io.Serializable;

import java.time.LocalDateTime;


@Table(name = "Account", indexes = {
        @Index(name = "UQ__Account__AB6E6164B485A157", columnList = "email", unique = true),
        @Index(name = "UQ__Account__F3DBC57267F2E915", columnList = "username", unique = true)
})
@Entity
@Data
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
	
	@NotEmpty
	@NotNull
    @Column(name = "username", nullable = false, length = 20)
    private String username;
    
    @Column(name = "fullname")
    @Size(max = 30)
    private String fullname;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "gender", nullable = false)
    private Boolean gender = false;

    @Column(name = "address", length = 225)
    private String address;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "img", length = 225)
    private String img;

    @Column(name = "idCard", nullable = false, length = 50)
    private String id_Card;
    
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role_Id;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "is_enable", nullable = false)
    private Boolean isEnable = false;

    @Column(name = "created_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    
}