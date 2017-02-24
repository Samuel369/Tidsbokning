package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BookingHandler bh = new BookingHandler();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	static TimeHandler th = new TimeHandler();

	// Omformatterar input fr�n anv�ndaren till LocalDateTime.
	public LocalDateTime formatter(String time) throws IOException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		LocalDateTime formatDateTime = LocalDateTime.parse(time, formatter);

		return formatDateTime;
	}

	// Tar input fr�n anv�ndaren och omformatterar den till
	// LocalDateTime. Sedan skriver den ut det anv�ndaren angett och
	// l�gger till det till en arraylist.
	public void case1(BookingHandler bh, BufferedReader reader) throws IOException {
		System.out.println("Ange ditt namn: ");
		String name = reader.readLine();
		System.out.println("Ange �r [YYYY]: ");
		String dateYear = reader.readLine();
		System.out.println("Ange m�nad [MM]: ");
		String dateMonth = reader.readLine();
		System.out.println("Ange dag [DD]: ");
		String dateDay = reader.readLine();
		System.out.println("Ange tid [HH:MM]: ");
		String time = reader.readLine();
		String dateTime = dateYear + "-" + dateMonth + "-" + dateDay + " " + time;
		LocalDateTime b = th.formatter(dateTime);
		System.out.println("Sluttid tid [HH:MM]: ");
		String endTime = reader.readLine();
		String dateEndTime = dateYear + "-" + dateMonth + "-" + dateDay + " " + endTime;
		LocalDateTime e = th.formatter(dateEndTime);
		System.out.println("Fr�n: " + b.format(formatter) + " \nTill: " + e.format(formatter));
		BookedTime c = bh.createBooking(name, b, e);
		bh.addBooking(c);
	}

}
