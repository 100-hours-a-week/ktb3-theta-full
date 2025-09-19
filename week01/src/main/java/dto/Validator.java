package dto;

public class Validator {
	private final boolean isValid;
	private final String message;
	
	public Validator(boolean isValid, String message) {
		this.isValid = isValid;
		this.message = message;
	}
	
	public boolean isValid() {
		return this.isValid;
	}
	
	public String getMessage() {
		return this.message;
	}
}
