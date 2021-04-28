package com.demo.springjwt.repository;

import java.util.Optional;

import com.demo.springjwt.models.ERole;
import com.demo.springjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	// @Query("select d from Role d where d.name = :name")
	Optional<Role> findByName(ERole name);
}
