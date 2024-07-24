package it.gpedulla.libreria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gpedulla.libreria.model.Book;
import it.gpedulla.libreria.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Optional<Book> findById(Integer id) {
		return bookRepository.findById(id);
	}

	@Override
	public Book save(Book book) {
		
		return bookRepository.save(book);
	}

	@Override
	public Book update(Integer id, Book inputBook) {
		
		Optional<Book> libroCercato = bookRepository.findById(id);
		
		if(libroCercato.isEmpty()) {
			throw new IllegalArgumentException("Il libro cercato con id " + id + " non esiste");
		}
		
		Book libro = libroCercato.get();
		
		libro.setAuthor(inputBook.getAuthor());
		libro.setBorrowings(inputBook.getBorrowings());
		libro.setCategories(inputBook.getCategories());
		libro.setIsbn(inputBook.getIsbn());
		libro.setTitle(inputBook.getTitle());
		
		return bookRepository.save(libro);
 		
	}

	@Override
	public void delete(Integer id) {
		bookRepository.deleteById(id);
	}

}
