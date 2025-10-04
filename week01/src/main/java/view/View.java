package view;

import controller.BookController;
import dto.ResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class View {
	private final BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
	private final BookController bookController;
	
	public View(BookController bookController) {
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
	
	public Optional<String> getUsername() {
		System.out.print("유저 이름을 입력해주세요.\n> ");
		try {
			return Optional.of(reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public Optional<Integer> getInput(String message) {
		System.out.print(message + "\n> ");
		
		try {
			String answer = reader.readLine();
			return Optional.of(Integer.parseInt(answer));
		} catch (NumberFormatException e) {
			System.out.println("잘못된 입력입니다. 재시도해주세요.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public void showMessage(String message) {
		System.out.print(message);
	}
	
	public void viewBooks() {
		showMessage(bookController.viewBooks());
	}
	
	public void rentBook() {
		showMessage(bookController.viewBooks());
		
		String userName = getUsername().orElseThrow();
		Integer index = getInput("빌릴 도서의 번호를 선택해주세요.").orElseThrow();
		ResponseDto responseDto = bookController.rentBook(userName, index);
		showMessage(responseDto.message());
	}
	
	public void returnBook() {
		showMessage(bookController.viewBooks());
		
		String userName = getUsername().orElseThrow();
		int index = getInput("반납할 도서의 번호를 선택해주세요.").orElseThrow();
		ResponseDto responseDto = bookController.returnBook(userName, index);
		
		if (!responseDto.isValid() && responseDto.message().contains("연체")) {
			while (!responseDto.isValid()) {
				Integer fee = getInput(responseDto.message()).orElseThrow();
				responseDto = bookController.payLateFee(index, fee);
			}
		} else {
			showMessage(responseDto.message());
		}
	}
}
