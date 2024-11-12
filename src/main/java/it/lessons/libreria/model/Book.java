package it.lessons.libreria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Title cannot be null")
	@NotBlank(message = "Title cannot be empty")
	private String title;
	
	@NotNull(message = "Author cannot be null")
	@NotBlank(message = "Author cannot be empty")
	private String author;
	
	private Integer year;
	
	@NotNull(message = "Number of copies cannot be null")
	@Min(value = 0, message = "Number of copies must be greater or equal than 0")
	private Integer numberOfCopies;
	

	@NotNull(message = "Isbn code cannot be null")
	@NotBlank(message = "Isbn code cannot be empty")
	@Size(min = 13, max = 13, message = "Isbn code must have 13 chars")
	@Column(name = "isbn_code", length = 13, unique = true, nullable = false)
	private String isbn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", year=" + year + ", numberOfCopies="
				+ numberOfCopies + ", isbn=" + isbn + "]";
	}
}
