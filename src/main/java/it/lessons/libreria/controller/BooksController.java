package it.lessons.libreria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lessons.libreria.model.Book;
import it.lessons.libreria.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BookRepository bookRepo;

	@GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		
		List<Book> allBooks;
		
		if(keyword != null && !keyword.isBlank()) {
			allBooks = bookRepo.findByTitleContaining(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			allBooks = bookRepo.findAll();
		}
		

		model.addAttribute("books", allBooks);

		return "books/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable(name = "id") Long id, @RequestParam(name = "keyword", required = false) String keyword,
			Model model) {

		Optional<Book> bookOptional = bookRepo.findById(id);

		if (bookOptional.isPresent()) {
			model.addAttribute("book", bookOptional.get());
		}
		model.addAttribute("keyword", keyword);
		if(keyword == null || keyword.isBlank() || keyword.equals("null")) {
			model.addAttribute("bookUrl", "/books");
		} else {			
			model.addAttribute("bookUrl", "/books?keyword=" + keyword);
		}
		return "books/show";
	}
}
