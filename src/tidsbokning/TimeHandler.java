package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BookingHandler bh = new BookingHandler();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	static TimeHandler th = new TimeHandler();

	// Omformatterar input från användaren till LocalDateTime.
	public LocalDateTime formatter(String time) throws IOException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime formatDateTime = LocalDateTime.parse(time, formatter);

		return formatDateTime;
	}

	// Tar input från användaren och omformatterar den till
	// LocalDateTime. Sedan skriver den ut det användaren angett och
	// lägger till det till en arraylist.
	public void case1(BookingHandler bh, BufferedReader reader) throws IOException {
		System.out.println("Ange ditt namn: ");
		String name = reader.readLine();
		System.out.println("Ange år [YYYY]: ");
		String dateYear = reader.readLine();
		System.out.println("Ange månad [MM]: ");
		String dateMonth = reader.readLine();
		System.out.println("Ange dag [DD]: ");
		String dateDay = reader.readLine();
		System.out.println("Ange starttid [HH:MM]: ");
		String time = reader.readLine();
		String dateTime = dateYear + "-" + dateMonth + "-" + dateDay + " " + time;
		System.out.println("Ange sluttid [HH:MM]: ");
		String endTime = reader.readLine();
		String dateEndTime = dateYear + "-" + dateMonth + "-" + dateDay + " " + endTime;
		try {
			LocalDateTime b = th.formatter(dateTime);
			LocalDateTime e = th.formatter(dateEndTime);
			System.out.println("Från: " + b.format(formatter) + " \nTill: " + e.format(formatter));
			BookedTime c = bh.createBooking(name, b, e);
			bh.addBooking(c);
		} catch (DateTimeParseException e) {
			System.out.println("Ett fel: " + e.getClass().getSimpleName() + " : " + e.getMessage()
					+ "\nMåste ange datum och tid enligt formatet som efterfrågas.");
		}
	}

}
