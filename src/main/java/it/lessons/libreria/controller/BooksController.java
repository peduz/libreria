package it.lessons.libreria.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.lessons.libreria.model.Book;
import it.lessons.libreria.model.Borrowing;
import it.lessons.libreria.repository.BookRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BookRepository bookRepo;

	@GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {

		List<Book> allBooks;

		if (keyword != null && !keyword.isBlank()) {
			allBooks = bookRepo.findByTitleContaining(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			allBooks = bookRepo.findAll();
		}

		model.addAttribute("books", allBooks);

		return "books/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable(name = "id") Long id,
			@RequestParam(name = "keyword", required = false) String keyword, Model model) {

		Optional<Book> bookOptional = bookRepo.findById(id);

		if (bookOptional.isPresent()) {
			model.addAttribute("book", bookOptional.get());
		}
		model.addAttribute("keyword", keyword);
		if (keyword == null || keyword.isBlank() || keyword.equals("null")) {
			model.addAttribute("bookUrl", "/books");
		} else {
			model.addAttribute("bookUrl", "/books?keyword=" + keyword);
		}
		return "books/show";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("book", new Book());

		return "books/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors())  {
			return "books/create";
		}
		
		bookRepo.save(formBook);
		
		redirectAttributes.addFlashAttribute("successMessage", "Book created");
		
		return "redirect:/books";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		
		model.addAttribute("book", bookRepo.findById(id).get());
		
		return "books/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable Long id,
	   @Valid @ModelAttribute("book") Book formBook, 
	   BindingResult bindingResult, 
	   Model model) {
	   
	   if(bindingResult.hasErrors()) {
	      return "/books/edit";
	   }
	   
	   Book book = bookRepo.findById(id).get();
	   
	   if(!formBook.getIsbn().equals(book.getIsbn())) {
		   bindingResult.addError(new ObjectError("isbn", "ISBN code cannot be changed"));
		   return "/books/edit";
	   }
	   
	   bookRepo.save(formBook);
	   
	   return "redirect:/books";
	}
	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {

	   bookRepo.deleteById(id);
	   
	   return "redirect:/books";
	}
	
	@GetMapping("/{id}/borrow")
	public String borrow(@PathVariable Long id, Model model) {
		
		Book book = bookRepo.findById(id).get();
		
		Borrowing borrow = new Borrowing();
		borrow.setBook(book);
		borrow.setBorrowingDate(LocalDate.now());
		
		model.addAttribute("borrowing", borrow);
		
		return "borrowings/edit";
	}
}
