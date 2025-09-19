package view;

import controller.BookController;
import dto.Validator;

import java.io.BufferedReader;
import java.io.IOException;

public class View {
	private final BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
	private BookController bookController;
	
	public void setController(BookController bookController) {
		this.bookController = bookController;
	}
	
	public void runApplication() {
		showWelcomeMessage();
		displayMenu();
	}
	
	public void showWelcomeMessage() {
		System.out.println("=========================");
		System.out.println("책 대여 시스템");
	}
	
	public void displayMenu() {
		String input;
		try {
			do {
				showMenuMessage();
				input = reader.readLine();
				switch (input) {
					case "1":
						viewBooks();
						break;
					case "2":
						rentBook();
						break;
					case "3":
						returnBook();
						break;
					case "4":
						System.out.println("이용해주셔서 감사합니다. 종료합니다.");
						break;
					default:
						System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
						System.out.print("> ");
						break;
				}
			} while (!input.equals("4"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMenuMessage() {
		System.out.println("=========================");
		System.out.println("번호를 입력해주세요.");
		System.out.println("1. 책 목록 보기");
		System.out.println("2. 책 대여");
		System.out.println("3. 책 반납");
		System.out.println("4. 종료");
		System.out.println("입력을 통해 선택해주세요.");
		System.out.print("> ");
	}
	
	public String getUsername() {
		System.out.print("유저 이름을 입력해주세요.\n> ");
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getInput(String message) {
		System.out.print(message + "\n> ");
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void showMessage(String message) {
		System.out.println(message);
	}
	
	public void viewBooks() {
		showMessage(bookController.viewBooks());
	}
	
	public void rentBook() {
		showMessage(bookController.viewBooks());
		
		Validator vali = bookController.rentBook(getUsername(), Integer.parseInt(getInput("빌릴 도서의 번호를 선택해주세요.")));
		showMessage(vali.getMessage());
	}
	
	public void returnBook() {
		showMessage(bookController.viewBooks());
		
		Validator vali = bookController.returnBook(getUsername(), Integer.parseInt(getInput("반납할 도서의 번호를 선택해주세요.")));
		showMessage(vali.getMessage());
	}
	
}
