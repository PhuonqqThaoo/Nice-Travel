package com.nicetravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role_Id;

    @Column(name = "isDeleted", nullable = false)
    private Boolean is_deleted = false;

}