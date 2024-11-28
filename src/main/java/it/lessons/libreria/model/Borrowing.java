package it.lessons.libreria.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
public class Borrowing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Borrowing date cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate borrowingDate;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
	private LocalDate returnDate;
	
	private String note;
	
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	@JsonBackReference
	private Book book;

	private boolean valid;
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(LocalDate borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Borrowing [id=" + id + ", borrowingDate=" + borrowingDate + ", returnDate=" + returnDate + ", note="
				+ note + ", book=" + book + "]";
	}
	
}
