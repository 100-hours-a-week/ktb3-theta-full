package controller;

import dto.Validator;
import service.BookService;
import view.View;

public class BookController {
	private static final int LATE_FEE = 100;
	
	private final View view;
	private final BookService bookService;
	
	public BookController(View view, BookService bookService) {
		this.view = view;
		this.bookService = bookService;
	}
	
	public String viewBooks() {
		return bookService.viewBooks();
	}
	
	public Validator rentBook(String userName, int index) {
		return bookService.rentBook(userName, index-1);
	}
	
	public Validator returnBook(String userName, int index) {
		Validator validator = bookService.returnBook(userName, index);
		
		while(!validator.isValid() && validator.getMessage().contains("연체")) {
			validator = payLateFee(Integer.parseInt(
					view.getInput(validator.getMessage() + "연체료는 " + LATE_FEE + "원 입니다.")
			));
		}
		return bookService.processReturn(index);
	}
	
	public Validator payLateFee(int fee) {
		if(fee != LATE_FEE) {
			return new Validator(false, "연체료는 " + LATE_FEE + "원 입니다.");
		}
		return new Validator(true, "연체료가 납부됐습니다.");
	}
}
