package it.lessons.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.libreria.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
