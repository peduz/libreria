package it.gpedulla.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gpedulla.libreria.model.Category;
import it.gpedulla.libreria.repository.CategoryRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@GetMapping
	public String index(Model model) {
		
		model.addAttribute("listaCategorie", categoryRepository.findAll());
		model.addAttribute("categoria", new Category());
		
		return "/categories/index";
	}
	
	@PostMapping("/create")
	public String store(
			@Valid @ModelAttribute("categoria") Category categoria,
			BindingResult bindingResult,
			Model model) {
		
		
		if(categoria.getCategory() != null) {
			Category categoriaFiltrata = 
					categoryRepository.findByCategoryIgnoreCase(categoria.getCategory());
			if(categoriaFiltrata != null) {
				bindingResult.addError(new ObjectError("Errore di inserimento", 
						"La categoria inserita esiste già"));
			}
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("listaCategorie", categoryRepository.findAll());
			model.addAttribute("categoria", new Category());
			return "/categories/index";
		}
		
		categoryRepository.save(categoria);
		
		return "redirect:/categories";
	}
	
}
