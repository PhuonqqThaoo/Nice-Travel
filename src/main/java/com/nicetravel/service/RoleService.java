package com.nicetravel.service;


import com.nicetravel.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRole();

    Role findById(Integer id);

    Role createRole(Role role);

    Role updateRole(Role role);

    void deleteRole(Integer id);

    Role findByRoleName(String roleName);
}
