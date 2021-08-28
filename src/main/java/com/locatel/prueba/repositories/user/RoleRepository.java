package com.locatel.prueba.repositories.user;

import com.locatel.prueba.models.user.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
