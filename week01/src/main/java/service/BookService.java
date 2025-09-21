package service;

import domain.Book;
import dto.ResponseDto;
import repository.BookRepository;
import util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookService {
	
	private final DateUtil dateUtil;
	private final BookRepository bookRepository;
	private final FileService fileService;
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	public BookService(DateUtil dateUtil, BookRepository bookRepository, FileService fileService) {
		this.dateUtil = dateUtil;
		this.bookRepository = bookRepository;
		this.fileService = fileService;
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
	
	public ResponseDto rentBook(String userName, int index) {
		if(isValid(index)) {
			return new ResponseDto(false, "잘못된 도서 번호입니다.");
		}
		
		Book book = bookRepository.getBook(index);
		if (book.getRentedBy().equals(userName)) {
			return new ResponseDto(false, "사용자가 이미 대여 중인 도서입니다.");
		} else if (!book.getRentedBy().isEmpty()) {
			return new ResponseDto(false, "이미 다른 사용자가 대여 중인 도서입니다. 대여 만료일은 " + book.getRentalDate() + "이니 기다려주세요.");
		}
		
		bookRepository.changeBookStatus(book, userName, dateUtil.getReturnDueDate());
		executorService.execute(fileService::writeData);
		return new ResponseDto(true, "성공적으로 대여되었습니다. 2주 뒤까지 반납해주세요!");
	}
	
	public ResponseDto returnBook(String userName, int index) {
		if(isValid(index)) {
			return new ResponseDto(false, "잘못된 도서 번호입니다.");
		}
		
		Book book = bookRepository.getBook(index);
		if (book.getRentedBy().isEmpty() || !book.getRentedBy().equals(userName)) {
			return new ResponseDto(false, "대여 중인 도서가 아닙니다.");
		} if(isLate(book)) {
			return new ResponseDto(false, "연체되었습니다.");
		}
		return processReturn(book);
	}
	
	public ResponseDto processReturn(int index) {
		bookRepository.changeBookStatus(bookRepository.getBook(index-1), "", "");
		executorService.execute(fileService::writeData);
		return new ResponseDto(true, "성공적으로 반납되었습니다. 이용해주셔서 감사합니다!");
	}
	
	public ResponseDto processReturn(Book book) {
		bookRepository.changeBookStatus(book, "", "");
		executorService.execute(fileService::writeData);
		return new ResponseDto(true, "성공적으로 반납되었습니다. 이용해주셔서 감사합니다!");
	}
	
	private boolean isLate(Book book) {
		return LocalDateTime.now().isAfter(
				dateUtil.parseStringToDate(book.getRentalDate()));
	}
	
	public void closeThread() {
		executorService.shutdown();
	}
}
