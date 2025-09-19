package service;

import domain.*;
import repository.BookRepository;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileService {
	private static final File file = new File("week01/src/main/java/data/books.csv");
	private static final String PHYSICAL = "physical";
	private static final String E = "e";
	private static final String AUDIO = "audio";
	
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
	
	private final BookRepository bookRepository;
	
	public FileService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public void readData() {
		try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
			String data = fileReader.readLine();
			
			while ((data = fileReader.readLine()) != null) {
				String[] details = data.split(",", -1);
				switch (details[0]) {
					case PHYSICAL:
						bookRepository.addBooks(new PhysicalBook(details[1], details[2], details[3], details[4]));
						break;
					case E:
						bookRepository.addBooks(new DigitalBook(details[1], details[2], details[3], details[4], details[5]));
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
				writer.write(book.toCSV());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
