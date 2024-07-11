package it.gpedulla.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gpedulla.libreria.model.Borrowing;
import it.gpedulla.libreria.repository.BorrowingRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

	@Autowired
	private BorrowingRepository borrowingRepository;
	
	
	@PostMapping("/create")
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
