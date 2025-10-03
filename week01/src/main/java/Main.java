import controller.BookController;
import repository.BookRepository;
import service.BookService;
import service.FileService;
import util.DateUtil;
import view.View;

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) {
		DateUtil dateUtil = new DateUtil();
		BookRepository bookRepository = new BookRepository();
		
		FileService fileService = new FileService(bookRepository);
		BookService bookService = new BookService(dateUtil, bookRepository, fileService);
		
		
		BookController bookController = new BookController(bookService);
		View view = new View(bookController);
		
		fileService.readData();
		view.runApplication();
		bookService.closeThread();
	}
}