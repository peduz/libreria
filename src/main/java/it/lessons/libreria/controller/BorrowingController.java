package it.lessons.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.libreria.model.Book;
import it.lessons.libreria.model.Borrowing;
import it.lessons.libreria.repository.BookRepository;
import it.lessons.libreria.repository.BorrowingRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowing")
public class BorrowingController {

	@Autowired
	private BorrowingRepository borrowingRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("borrowing") Borrowing borrowing,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "borrowings/edit";
		}
		
		if(borrowing.getBook().getNumberOfCopies() <= 0) {
			bindingResult.addError(new ObjectError("errorNumCopies", 
					"Cannot borrow this book because there aren't enough copies"));
			return "borrowings/edit";
		}
		
		borrowing.setValid(true);
		borrowingRepository.save(borrowing);
		
		Book book = borrowing.getBook();
		book.setNumberOfCopies(book.getNumberOfCopies() - 1);
		bookRepository.save(book);
		
		return "redirect:/books/show/" + borrowing.getBook().getId();
	}
	
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		
		Borrowing borrowing = borrowingRepository.findById(id).get();
		
		model.addAttribute("borrowing", borrowing);
		model.addAttribute("edit", true);
		
		return "borrowings/edit";
	}
	
	
	@PostMapping("/edit/{id}")
	public String edit(@Valid @ModelAttribute("borrowing") Borrowing borrowing,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "borrowings/edit";
		}
		if(borrowing.getReturnDate() == null) {
			bindingResult.addError(new FieldError("returnDateError", "returnDate", "Return date cannot be null"));
			model.addAttribute("edit", true);
			return "borrowings/edit";
		}
		
		if(!borrowing.isValid()) {
			bindingResult.addError(new ObjectError("invalidBorrow", "Cannot edit an invalid borrowing"));
			model.addAttribute("edit", true);
			return "borrowings/edit";
		}
		
		borrowing.setValid(false);
		borrowingRepository.save(borrowing);
		
		Book book = borrowing.getBook();
		book.setNumberOfCopies(book.getNumberOfCopies() + 1);
		bookRepository.save(book);	
		
		return "redirect:/books/show/" + borrowing.getBook().getId();
	}
	
}
