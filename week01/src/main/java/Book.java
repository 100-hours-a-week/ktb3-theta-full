public class Book {
	private final String title;
	private final String author;
	private String rentalDate;
	private String rentedBy;
	
	public Book(String title, String author, String rentalDate, String rentedBy) {
		this.title = title;
		this.author = author;
		this.rentalDate = rentalDate;
		this.rentedBy = rentedBy;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getRentalDate() {
		return this.rentalDate;
	}
	
	public void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}
	
	public String getRentedBy() {
		return this.rentedBy;
	}
	
	public void setRentedBy(String rentedBy) {
		this.rentedBy = rentedBy;
	}
	
	public String toCSV() {
		return this.title + "," + this.author + "," + this.rentalDate + "," + this.rentedBy;
	}
}