package com.poc.authserver.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.poc.authserver.model.User;

public interface UserRepository extends CrudRepository<User, Long>
{
	Optional<User> findByUsername(String username);
}