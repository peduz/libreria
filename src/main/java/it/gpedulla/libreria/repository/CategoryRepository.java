package it.gpedulla.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.gpedulla.libreria.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	public Category findByCategoryIgnoreCase(String category);
}
