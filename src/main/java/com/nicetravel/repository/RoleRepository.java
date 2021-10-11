package com.nicetravel.repository;

import com.nicetravel.entity.Account;
import com.nicetravel.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT u FROM Role u WHERE u.role = ?1")
    Role findByRoleName(String roleName);
}
