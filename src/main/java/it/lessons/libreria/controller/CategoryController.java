package it.lessons.libreria.controller;

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

import it.lessons.libreria.model.Book;
import it.lessons.libreria.model.Category;
import it.lessons.libreria.repository.CategoryRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@GetMapping
	public String index(Model model) {
		
		List<Category> all = categoryRepository.findAll();
		
		model.addAttribute("categories", all);
		model.addAttribute("cat", new Category());
		
		return "categories/index";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute(name = "cat") Category category, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			
			List<Category> all = categoryRepository.findAll();
			
			model.addAttribute("categories", all);
			model.addAttribute("cat", new Category());
			return "categories/index";
		}
		
		categoryRepository.save(category);
		
		return "redirect:/categories";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, Model model) {
		
		
		Category category = categoryRepository.findById(id).get();
		
		for(Book book : category.getBooks()) {
			
			book.getCategories().remove(category);
			
		}
		
		categoryRepository.deleteById(id);
		
		return "redirect:/categories";
	}
}
