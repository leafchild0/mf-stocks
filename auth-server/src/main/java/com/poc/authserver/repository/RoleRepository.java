package com.poc.authserver.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.poc.authserver.model.Role;
import com.poc.authserver.model.RoleName;

public interface RoleRepository extends CrudRepository<Role, Long>
{
	Optional<Role> findByName(RoleName roleUser);
}