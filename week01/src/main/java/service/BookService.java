package service;

import domain.Book;
import dto.Validator;
import repository.BookRepository;
import util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class BookService {
	
	private final DateUtil dateUtil;
	private final BookRepository bookRepository;
	
	public BookService(DateUtil dateUtil, BookRepository bookRepository) {
		this.dateUtil = dateUtil;
		this.bookRepository = bookRepository;
	}
	
	public boolean isValid(int index) {
		return index < 0 || index > bookRepository.getBooksSize();
	}
	
	public String viewBooks() {
		StringBuilder sb = new StringBuilder();
		
		List<Book>	 books = bookRepository.getBooks();
		for(int i = 0;  i < books.size();  i++) {
			sb.append(i+1).append(". ").append(books.get(i).toString()).append(" | ").append(books.get(i).getRentedBy().isEmpty() ? "대여 가능" : "대여 불가능, 대여자의 반납일은 " + books.get(i).getRentalDate().substring(0, 10)).append("\n");
		}
		return sb.toString();
	}
	
	public Validator rentBook(String userName, int index) {
		if(isValid(index)) {
			return new Validator(false, "잘못된 도서 번호입니다.");
		}
		
		Book book = bookRepository.getBook(index);
		if (book.getRentedBy().equals(userName)) {
			return new Validator(false, "사용자가 이미 대여 중인 도서입니다.");
		} else if (!book.getRentedBy().isEmpty()) {
			return new Validator(false, "이미 다른 사용자가 대여 중인 도서입니다. 대여 만료일은 " + book.getRentalDate() + "이니 기다려주세요.");
		}
		
		bookRepository.changeBookStatus(book, userName, dateUtil.getReturnDueDate());
		return new Validator(true, "성공적으로 대여되었습니다. 2주 뒤까지 반납해주세요!");
	}
	
	public Validator returnBook(String userName, int index) {
		if(isValid(index)) {
			return new Validator(false, "잘못된 도서 번호입니다.");
		}
		
		Book book = bookRepository.getBook(index);
		if (book.getRentedBy().isEmpty() || !book.getRentedBy().equals(userName)) {
			return new Validator(false, "대여 중인 도서가 아닙니다.");
		} if(isLate(book)) {
			return new Validator(false, "연체되었습니다.");
		}
		return processReturn(book);
	}
	
	public Validator processReturn(int index) {
		bookRepository.changeBookStatus(bookRepository.getBook(index), "", "");
		return new Validator(true, "성공적으로 반납되었습니다. 이용해주셔서 감사합니다!");
	}
	
	public Validator processReturn(Book book) {
		bookRepository.changeBookStatus(book, "", "");
		return new Validator(true, "성공적으로 반납되었습니다. 이용해주셔서 감사합니다!");
	}
	
	private boolean isLate(Book book) {
		return LocalDateTime.now().isAfter(
				dateUtil.parseStringToDate(book.getRentalDate()));
	}
}
