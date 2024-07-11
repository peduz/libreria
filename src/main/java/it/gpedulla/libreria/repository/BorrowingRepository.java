package it.gpedulla.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.gpedulla.libreria.model.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer>{

}
