package it.gpedulla.libreria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gpedulla.libreria.model.Book;
import it.gpedulla.libreria.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@GetMapping
	public String index(Model model) {
		
		List<Book> libri = repository.findAll();
		
		model.addAttribute("list", libri);
		
		return "/books/index";
	}
	
}
