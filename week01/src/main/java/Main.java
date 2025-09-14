import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BookRentalApplication application = new BookRentalApplication();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("=========================");
		System.out.println("책 대여 시스템");
		
		application.readData();
		int input;
		do {
			application.displayMenu();
			input = scanner.nextInt();
			
			switch(input) {
				case 1:
					application.viewBooks();
					break;
				case 2:
					application.rentBook();
					break;
				case 3:
					application.returnBook();
					break;
				case 4:
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
					System.out.print("> ");
			}
		} while(input != 4);
		
		application.writeData();
		System.out.println("이용해주셔서 감사합니다.");
		scanner.close();
	}
}