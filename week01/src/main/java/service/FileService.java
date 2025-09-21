package service;

import domain.*;
import repository.BookRepository;

import java.io.*;
import java.util.List;

public class FileService {
	private static final File file = new File("week01/src/main/java/data/books.csv");
	private static final String PHYSICAL = "physical";
	private static final String E = "e";
	private static final String AUDIO = "audio";
	
	private final BookRepository bookRepository;
	
	public FileService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public void readData() {
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
			String data;
			
			while ((data = fileReader.readLine()) != null) {
				String[] details = data.split(",", -1);
				switch (details[0]) {
					case PHYSICAL:
						bookRepository.addBooks(new PhysicalBook(details[1], details[2], details[3], details[4]));
						break;
					case E:
						bookRepository.addBooks(new EBook(details[1], details[2], details[3], details[4], details[5]));
						break;
					case AUDIO:
						bookRepository.addBooks(new AudioBook(details[1], details[2], details[3], details[4], details[5], Integer.parseInt(details[6]), details[7]));
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeData() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			List<Book> books = bookRepository.getBooks();
			
			for(Book book : books) {
				if(book instanceof PhysicalBook) {
					writer.write(PHYSICAL+",");
				} else if(book instanceof EBook) {
					writer.write(E+",");
				} else if (book instanceof AudioBook) {
					writer.write(AUDIO+",");
				}
				writer.write(book.toCSV());
				writer.newLine();
				System.out.println("서버에 데이터 저장이 완료되었습니다.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
