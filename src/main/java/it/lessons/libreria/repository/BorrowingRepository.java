package it.lessons.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.libreria.model.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

}
