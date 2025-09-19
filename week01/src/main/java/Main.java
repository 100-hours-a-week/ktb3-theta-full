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
		BookRepository bookRepository = new BookRepository();
		BookService bookService = new BookService(new DateUtil(), bookRepository);
		View view = new View();
		BookController bookController = new BookController(view, bookService);
		FileService fileService = new FileService(bookRepository);
		view.setController(bookController);
		
		fileService.readData();
		view.runApplication();
	}
}