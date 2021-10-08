package com.nicetravel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Table(name = "roles")
@Entity
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "role_Id")
    List<Account> authorities;

}