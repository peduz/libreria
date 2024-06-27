package it.gpedulla.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.gpedulla.libreria.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
