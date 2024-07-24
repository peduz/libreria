package it.gpedulla.libreria.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gpedulla.libreria.model.Book;
import it.gpedulla.libreria.response.Payload;
import it.gpedulla.libreria.service.BookService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BookRestController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("{id}")
	public ResponseEntity<Payload<Book>> get(@PathVariable("id") Integer idBook) {
		
		Optional<Book> libro = bookService.findById(idBook);
		
		if(libro.isPresent()) {
			return ResponseEntity.ok(new Payload<Book>(libro.get(), null, HttpStatus.OK));
		} else {
			return new ResponseEntity<Payload<Book>>(
					new Payload<Book>(null, "Libro con id " + idBook + " non trovato", 
					HttpStatus.NOT_FOUND), 
					HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	
	@PostMapping
	public ResponseEntity store(
			@Valid @RequestBody Book book) {
		try {
			Book libroSalvato = bookService.save(book);
			return ResponseEntity.ok(libroSalvato);
		} catch(Exception e) {
			return new ResponseEntity<>("Errore nel salvataggio del libro", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Integer idBook,
			@Valid @RequestBody Book book) {
		
		try {
			Book bookUpdated = bookService.update(idBook, book);
			
			return ResponseEntity.ok(bookUpdated);
		} catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), 
					HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			return new ResponseEntity<>("Errore nel salvataggio del libro", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
		try {
			bookService.delete(id);
			return ResponseEntity.ok("Cancellazione effettuata");
		} catch(Exception e) {
			return new ResponseEntity<>("Errore nel salvataggio del libro", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
