package it.lessons.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.libreria.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
