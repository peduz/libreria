package it.gpedulla.libreria.service;

import java.util.Optional;

import it.gpedulla.libreria.model.Book;

public interface BookService {

	public Optional<Book> findById(Integer id);
	
	/*
	 * Salvo il book passato come argomento
	 * e restituisco l'elemento appena salvato
	 */
	public Book save(Book book);
	
	
	public Book update(Integer id, Book book);
	
	public void delete(Integer id);
}
