public class DigitalBook extends Book {
	private final String fileFormat;

	public DigitalBook(String title, String author, String rentalDate, String rentedBy, String fileFormat) {
		super(title, author, rentalDate, rentedBy);
		this.fileFormat = fileFormat;
	}
	
	public String getFileFormat() {
		return this.fileFormat;
	}
	
	@Override
	public String toCSV() {
		return super.toCSV() + "," + this.fileFormat;
	}
}