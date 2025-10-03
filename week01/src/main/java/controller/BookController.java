package controller;

import dto.ResponseDto;
import service.BookService;

public class BookController {
	private final BookService bookService;
	
	public BookController(BookService bookService) {
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
		
		if(!responseDto.isValid() && responseDto.message().contains("연체")) {
			return responseDto;
		}
		return bookService.processReturn(index);
	}
	
	public ResponseDto payLateFee(int fee) {
		return bookService.payLateFee(fee);
	}
}
