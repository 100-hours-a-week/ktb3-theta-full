package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
	
	public String getReturnDueDate() {
		return LocalDateTime.now().plusWeeks(2).toString();
	}
	
	public LocalDateTime parseStringToDate(String date) {
		return LocalDateTime.parse(date, dtf);
	}
}
