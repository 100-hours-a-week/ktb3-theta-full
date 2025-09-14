public class AudioBook extends DigitalBook {
	private final int duration;
	private final String narrator;

	public AudioBook(String title, String author, String rentalDate, String rentedBy, String fileFormat, int duration, String narrator) {
		super(title, author, rentalDate, rentedBy, fileFormat);
		this.duration = duration;
		this.narrator = narrator;
	}

	public String getNarrator() {
		return this.narrator;
	}
	
	@Override
	public String toCSV() {
		return super.toCSV() + "," + this.duration + "," + this.narrator;
	}
}