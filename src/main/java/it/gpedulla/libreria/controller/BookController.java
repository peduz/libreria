package it.gpedulla.libreria.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gpedulla.libreria.model.Book;
import it.gpedulla.libreria.model.Borrowing;
import it.gpedulla.libreria.repository.BookRepository;
import it.gpedulla.libreria.repository.BorrowingRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BorrowingRepository borrowingRepository;
	
	@GetMapping
	public String index(Model model, 
			@RequestParam(name = "author", required = false) String author) {
		
		List<Book> libri = new ArrayList<>();
		
		
		if(author == null || author.isBlank()) {
			libri = bookRepository.findAll();
		} else {
			libri = bookRepository.findByAuthorOrderByIdDesc(author);
		}
		
		model.addAttribute("list", libri);
		
		return "/books/index";
	}
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer bookId, Model model) {
		
		model.addAttribute("book", bookRepository.findById(bookId).get());
		
		return "/books/show";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		
	    model.addAttribute("book", new Book());
	    
	    return "/books/create";
	}
	
	@PostMapping("/create")
	public String store(
		@Valid @ModelAttribute("book") Book formBook,
	   BindingResult bindingResult,
	   Model model){
		
	   if(bindingResult.hasErrors()) {
	      return "/books/create";
	   }

	   bookRepository.save(formBook);

	   return "redirect:/books";
	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		
		model.addAttribute("book", bookRepository.findById(id).get());
		
		return "/books/edit";
	}
	
	
	@PostMapping("/edit/{id}")
	public String update(
			@Valid @ModelAttribute("book") Book book,
			BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/books/edit";
		}
		
		bookRepository.save(book);
		
		return "redirect:/books";
	}
	
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		
		bookRepository.deleteById(id);
		
		return "redirect:/books";
	}
	
	
	@GetMapping("/{id}/borrow")
	public String borrow(@PathVariable("id") Integer id, Model model) {
		
		Book book = bookRepository.findById(id).get();
		Borrowing prestito = new Borrowing();
		prestito.setBorrowingDate(LocalDateTime.now());
		prestito.setBook(book);
		
		model.addAttribute("prestito", prestito);
		
		return "borrowings/edit";
	}
	
	@PostMapping("/borrow/create")
	public String store(
			@Valid @ModelAttribute("prestito") Borrowing prestito,
			BindingResult bindingResult,
			Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/borrowings/edit";
		}
		
		borrowingRepository.save(prestito);
		
		return "redirect:/books/show/" + prestito.getBook().getId();
	}
	
}
