package it.lessons.libreria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.libreria.model.Book;
import it.lessons.libreria.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping
	public String index(Model model) {
		List<Book> allBooks = bookRepo.findAll();
		
		model.addAttribute("books", allBooks);
		
		return "books/index";
	}
}
