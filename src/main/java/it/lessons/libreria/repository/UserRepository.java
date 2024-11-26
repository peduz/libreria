package it.lessons.libreria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.libreria.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
