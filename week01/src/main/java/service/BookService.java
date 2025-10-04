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
	private static final int LATE_FEE = 100;
	
	private final DateUtil dateUtil;
	private final BookRepository bookRepository;
	private final FileService fileService;
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	public BookService(DateUtil dateUtil, BookRepository bookRepository, FileService fileService) {
		this.dateUtil = dateUtil;
		this.bookRepository = bookRepository;
		this.fileService = fileService;
	}
	
	// 도서 번호 유효성 확인
	public boolean isValid(int index) {
		return index < 0 || index > bookRepository.getBooksSize();
	}
	
	// 도서 목록 조회
	public String viewBooks() {
		StringBuilder sb = new StringBuilder();
		
		List<Book>	 books = bookRepository.getBooks();
		for(int i = 0;  i < books.size();  i++) {
			sb.append(i+1).append(". ").append(books.get(i).toString()).append(" | ").append(books.get(i).getRentedBy().isEmpty() ? "대여 가능" : "대여 불가능, 대여자의 반납일은 " + books.get(i).getRentalDate().substring(0, 10)).append("\n");
		}
		return sb.toString();
	}
	
	// 도서 대여
	public ResponseDto rentBook(String userName, int index) {
		Book book = bookRepository.getBook(index);
		if (book.getRentedBy().equals(userName)) {
			return new ResponseDto(false, "사용자가 이미 대여 중인 도서입니다.\n");
		} else if (!book.getRentedBy().isEmpty()) {
			return new ResponseDto(false, "이미 다른 사용자가 대여 중인 도서입니다. 대여 만료일은 " + dateUtil.parseStringToShortDate(book.getRentalDate()) + "이니 기다려주세요.\n");
		}
		
		bookRepository.changeBookStatus(book, userName, dateUtil.getReturnDueDate());
		executorService.execute(fileService::writeData);
		return new ResponseDto(true, "성공적으로 대여되었습니다. 2주 뒤까지 반납해주세요!\n");
	}
	
	// 도서 반납 (연체 여부 확인)
	public ResponseDto returnBook(String userName, int index) {
		Book book = bookRepository.getBook(index);
		if (book.getRentedBy().isEmpty() || !book.getRentedBy().equals(userName)) {
			return new ResponseDto(false, "대여 중인 도서가 아닙니다.\n");
		} else if(isLate(book)) {
			return new ResponseDto(false, "연체되었습니다. 연체료 " + LATE_FEE + "를 납부해주세요.\n");
		} else {
			return processReturn(book);
		}
	}
	
	// 연체료 납부
	public ResponseDto payLateFee(int fee) {
		if(fee != LATE_FEE) {
			return new ResponseDto(false, "연체료는 " + LATE_FEE + "원 입니다.\n");
		}
		return new ResponseDto(true, "연체료가 납부됐습니다.\n");
	}
	
	// 반납 진행
	public ResponseDto processReturn(int index) {
		bookRepository.changeBookStatus(bookRepository.getBook(index-1), "", "");
		executorService.execute(fileService::writeData);
		return new ResponseDto(true, "성공적으로 반납되었습니다. 이용해주셔서 감사합니다!\n");
	}
	
	public ResponseDto processReturn(Book book) {
		bookRepository.changeBookStatus(book, "", "");
		executorService.execute(fileService::writeData);
		return new ResponseDto(true, "성공적으로 반납되었습니다. 이용해주셔서 감사합니다!\n");
	}
	
	private boolean isLate(Book book) {
		return LocalDateTime.now().isAfter(
				dateUtil.parseStringToDate(book.getRentalDate()));
	}
	
	public void closeThread() {
		executorService.shutdown();
	}
}
