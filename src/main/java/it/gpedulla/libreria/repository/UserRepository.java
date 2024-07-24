package it.gpedulla.libreria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.gpedulla.libreria.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
}
