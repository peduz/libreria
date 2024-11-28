package it.lessons.libreria.restcontroller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lessons.libreria.model.Book;
import it.lessons.libreria.repository.BookRepository;
import it.lessons.libreria.response.model.Payload;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BooksRestController {
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping
	public ResponseEntity<Payload<List<Book>>> index(@RequestParam(name="keyword", required = false) String keyword) {
		try {
			
			if(keyword != null && !keyword.isBlank()) {
				if(keyword.equals("pippo")) {
					throw new Exception("Errore nella keyword");
				}
				Payload<List<Book>> response = 
						new Payload<>("Ok", "200", bookRepo.findByTitleContaining(keyword));
				return new ResponseEntity<Payload<List<Book>>>(
						response, 
						HttpStatus.OK);
			} else {
				Payload<List<Book>> response = new Payload<>("Ok", "200", bookRepo.findAll());
				return ResponseEntity.ok(response);
			} 
		} catch(Exception e) {
			Payload<List<Book>> error = 
					new Payload<>("Errore nella ricerca per keyword: " + e.getMessage(), "400", null);
			return new ResponseEntity<Payload<List<Book>>>(error, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Payload<Book>> update(@PathVariable Long id, @RequestBody Book book) {
		
		try {
			
			Optional<Book> byId = bookRepo.findById(id);
			
			Book dbBook = byId.get();
			
			dbBook.setNumberOfCopies(book.getNumberOfCopies());
			
			bookRepo.save(dbBook);
			
			Payload<Book> payload = new Payload<Book>("Ok", "200", dbBook);
			return ResponseEntity.ok(payload);
		} catch(Exception e) {
			
			Payload<Book> payload = new Payload<Book>("Libro non trovato", "404", null);
			return new ResponseEntity<Payload<Book>>(payload, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		try {
			
			bookRepo.deleteById(id);
			
		} catch(Exception e) {
			System.err.println("Errore in fase di cancellazione " + e);
		}
	}
	
	@PostMapping
	public Book create(@Valid @RequestBody Book book) {
		return bookRepo.save(book);
	}
}
 