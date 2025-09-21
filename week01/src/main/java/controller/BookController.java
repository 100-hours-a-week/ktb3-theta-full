package controller;

import dto.ResponseDto;
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
	
	public ResponseDto rentBook(String userName, int index) {
		return bookService.rentBook(userName, index-1);
	}
	
	public ResponseDto returnBook(String userName, int index) {
		ResponseDto responseDto = bookService.returnBook(userName, index-1);
		
		while(!responseDto.isValid() && responseDto.message().contains("연체")) {
			responseDto = payLateFee(
					view.getInput(responseDto.message() + "연체료는 " + LATE_FEE + "원 입니다.")
			);
		}
		return bookService.processReturn(index);
	}
	
	public ResponseDto payLateFee(int fee) {
		if(fee != LATE_FEE) {
			return new ResponseDto(false, "연체료는 " + LATE_FEE + "원 입니다.");
		}
		return new ResponseDto(true, "연체료가 납부됐습니다.");
	}
}
