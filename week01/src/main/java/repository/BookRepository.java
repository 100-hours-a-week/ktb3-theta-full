package repository;

import domain.Book;

import java.util.*;

public class BookRepository {
	private static final List<Book> books = new ArrayList<>();
	
	public List<Book> getBooks() {
		return books;
	}
	
	public Book getBook(int index) {
		return books.get(index);
	}
	
	public void addBooks(Book book) {
		books.add(book);
	}
	
	public int getBooksSize() {
		return books.size();
	}
	
	public void changeBookStatus (Book book, String userName, String dueDate) {
		book.setRentedBy(userName);
		book.setRentalDate(dueDate);
	}
}
