package it.lessons.libreria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.libreria.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	
	public List<Book> findByTitleContaining(String title);
}
