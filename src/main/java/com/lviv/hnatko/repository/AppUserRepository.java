package com.lviv.hnatko.repository;


import com.lviv.hnatko.entity.AppUser;
import com.lviv.hnatko.entity.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findByRole(Role role);

}
