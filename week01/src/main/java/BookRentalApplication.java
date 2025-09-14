import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookRentalApplication {
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static final String DATA_PATH = "week01/src/main/java/data/";
	private final static File[] files = {new File(DATA_PATH+"physicalBooks.csv"), new File(DATA_PATH+"eBooks.csv"), new File(DATA_PATH+"audioBooks.csv")};
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
	
	private static List<PhysicalBook> physicalBooks = new ArrayList<>();
	private static List<EBook> eBooks = new ArrayList<>();
	private static List<AudioBook> audioBooks = new ArrayList<>();
	
	public void readData() throws IOException {
		for (int i = 0; i < files.length; i++) {
			BufferedReader reader = new BufferedReader(new FileReader(files[i]));
			String data;
			while ((data = reader.readLine()) != null) {
				String[] details = data.split(",", -1);
				switch (i) {
					case 0:
						physicalBooks.add(new PhysicalBook(details[0], details[1], details[2], details[3]));
						break;
					case 1:
						eBooks.add(new EBook(details[0], details[1], details[2], details[3], details[4]));
						break;
					case 2:
						audioBooks.add(new AudioBook(details[0], details[1], details[2], details[3], details[4], Integer.parseInt(details[5]), details[6]));
						break;
				}
			}
			reader.close();
		}
	}
	
	public void writeData() throws IOException {
		for (int i = 0; i < files.length; i++) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(files[i]));
			
			switch (i) {
				case 0:
					for (PhysicalBook physicalBook : physicalBooks) {
						writer.write(physicalBook.toCSV());
						writer.newLine();
					}
					break;
				case 1:
					for (EBook eBook : eBooks) {
						writer.write(eBook.toCSV());
						writer.newLine();
					}
					break;
				case 2:
					for (AudioBook audioBook : audioBooks) {
						writer.write(audioBook.toCSV());
						writer.newLine();
					}
					break;
			}
			writer.flush();
			writer.close();
		}
	}
	
	public void displayMenu() {
		System.out.println("=========================");
		System.out.println("번호를 입력해주세요.");
		System.out.println("1. 책 목록 보기");
		System.out.println("2. 책 대여");
		System.out.println("3. 책 반납");
		System.out.println("4. 종료");
		System.out.println("입력을 통해 선택해주세요.");
		System.out.print("> ");
	}
	public void viewBooks() {
		viewPhysicalBooks();
		viewEBooks();
		viewAudioBooks();
	}
	
	private void viewPhysicalBooks() {
		System.out.println("[일반 도서 목록]");
		for (int i = 0; i < physicalBooks.size(); i++) {
			PhysicalBook physicalBook =  physicalBooks.get(i);
			System.out.println((i+1) + ". " + physicalBook.getTitle() + "	|	" + (physicalBook.getRentedBy().isEmpty() ? "대여 가능" : "대여 불가능, 대여자의 반납일은 " + physicalBook.getRentalDate().substring(0, 10)));
		}
	}
	
	private void viewEBooks() {
		System.out.println("[이북 목록]");
		for (int i = 0; i < eBooks.size(); i++) {
			EBook eBook =  eBooks.get(i);
			System.out.println((i+1) + ". " + eBook.getTitle() + "." + eBook.getFileFormat() + "	|	" + (eBook.getRentedBy().isEmpty() ? "대여 가능" : "대여 불가능, 대여자의 반납일은 " + eBook.getRentalDate().substring(0, 10)));
		}
	}
	
	private void viewAudioBooks() {
		System.out.println("[오디오북 목록]");
		for (int i = 0; i < audioBooks.size(); i++) {
			AudioBook audioBook = audioBooks.get(i);
			System.out.println((i+1) + ". " + audioBook.getTitle() + "." + audioBook.getFileFormat() + "	|	" + (audioBook.getRentedBy().isEmpty() ? "대여 가능" : "대여 불가능, 대여자의 반납일은 " + audioBook.getRentalDate().substring(0, 10)));
		}
	}
	
	public void rentBook() throws IOException {
		System.out.print("유저 이름을 입력해주세요.\n> ");
		String userName = reader.readLine();
		System.out.print("일반, 이북, 오디오 중 빌릴 도서의 유형을 말씀해주세요.\n> ");
		String bookType = reader.readLine();
		int index;
		Book rentBook;
		
		switch (bookType) {
			case "일반":
				viewPhysicalBooks();
				System.out.print("빌릴 도서 번호를 말씀해주세요.\n> ");
				index = Integer.parseInt(reader.readLine());
				if(index <= 0 || index >= physicalBooks.size()) {
					System.out.println("잘못된 도서 번호입니다.");
					return;
				}
				rentBook = physicalBooks.get(index - 1);
				break;
			case "이북":
				viewEBooks();
				System.out.print("빌릴 도서 번호를 말씀해주세요.\n> ");
				index = Integer.parseInt(reader.readLine());
				if(index <= 0 || index >= eBooks.size()) {
					System.out.println("잘못된 도서 번호입니다.");
					return;
				}
				rentBook = eBooks.get(index - 1);
				break;
			case "오디오":
				viewAudioBooks();
				System.out.print("빌릴 도서 번호를 말씀해주세요.\n> ");
				index = Integer.parseInt(reader.readLine());
				if(index <= 0 || index >= audioBooks.size()) {
					System.out.println("잘못된 도서 번호입니다.");
					return;
				}
				rentBook = audioBooks.get(index - 1);
				break;
			default:
				System.out.println("잘못된 도서 유형입니다.");
				return;
		}
		
		if(rentBook.getRentedBy().equals(userName)) {
			System.out.println("사용자가 이미 대여 중인 도서입니다.");
			return;
		} else if(!rentBook.getRentedBy().isEmpty()) {
			System.out.println("이미 다른 사용자가 대여 중인 도서입니다. 대여 만료일은 " + rentBook.getRentalDate() + "이니 기다려주세요.");
			return;
		}
		rentBook.setRentedBy(userName);
		rentBook.setRentalDate(LocalDateTime.now().plusWeeks(2).toString());
		System.out.println("성공적으로 대여되었습니다. 2주 뒤까지 반납해주세요!");
	}
	
	public void returnBook() throws IOException {
		System.out.print("유저 이름을 입력해주세요.\n> ");
		String userName = reader.readLine();
		System.out.print("일반, 이북, 오디오 중 반납할 도서의 유형을 말씀해주세요.\n> ");
		String bookType = reader.readLine();
		int index;
		Book rentBook;
		
		switch (bookType) {
			case "일반":
				viewPhysicalBooks();
				System.out.print("반납할 도서 번호를 말씀해주세요.\n> ");
				index = Integer.parseInt(reader.readLine());
				if(index <= 0 || index >= physicalBooks.size()) {
					System.out.println("잘못된 도서 번호입니다.");
					return;
				}
				rentBook = physicalBooks.get(index - 1);
				break;
			case "이북":
				viewEBooks();
				System.out.print("반납할 도서 번호를 말씀해주세요.\n> ");
				index = Integer.parseInt(reader.readLine());
				if(index <= 0 || index >= eBooks.size()) {
					System.out.println("잘못된 도서 번호입니다.");
					return;
				}
				rentBook = eBooks.get(index - 1);
				break;
			case "오디오":
				viewAudioBooks();
				System.out.print("반납할 도서 번호를 말씀해주세요.\n> ");
				index = Integer.parseInt(reader.readLine());
				if(index <= 0 || index >= audioBooks.size()) {
					System.out.println("잘못된 도서 번호입니다.");
					return;
				}
				rentBook = audioBooks.get(index - 1);
				break;
			default:
				System.out.println("잘못된 도서 유형입니다.");
				return;
		}

		if(rentBook.getRentedBy().isEmpty() || !rentBook.getRentedBy().equals(userName)) {
			System.out.println("대여 중인 도서가 아닙니다.");
			return;
		}
		else if(LocalDateTime.now().isAfter(LocalDateTime.parse(rentBook.getRentalDate(), dtf))) {
			System.out.print("연체료 100원이 부과되었습니다. 연체료를 내주세요.\n> ");
			while(Integer.parseInt(reader.readLine()) != 100) {
				System.out.print("연체료는 100원입니다. 다시 내주세요.\n> ");
			}
		}
		
		rentBook.setRentedBy("");
		rentBook.setRentalDate("");
		System.out.println("성공적으로 반납되었습니다. 이용해주셔서 감사합니다!");
	}
}
