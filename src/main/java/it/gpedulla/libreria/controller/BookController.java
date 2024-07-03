package it.gpedulla.libreria.controller;

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
import it.gpedulla.libreria.repository.BookRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@GetMapping
	public String index(Model model, 
			@RequestParam(name = "author", required = false) String author) {
		
		List<Book> libri = new ArrayList<>();
		
		
		if(author == null || author.isBlank()) {
			libri = repository.findAll();
		} else {
			libri = repository.findByAuthorOrderByIdDesc(author);
		}
		
		model.addAttribute("list", libri);
		
		return "/books/index";
	}
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer bookId, Model model) {
		
		model.addAttribute("book", repository.getReferenceById(bookId));
		
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

	   repository.save(formBook);

	   return "redirect:/books";
	}
}
