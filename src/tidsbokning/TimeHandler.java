package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// Omformatterar input från användaren till LocalDateTime.
	public LocalDateTime formatter(String time) throws IOException {

		LocalDateTime formatDateTime = LocalDateTime.parse(time, formatter);

		return formatDateTime;
	}

	// Tar input från användaren och omformatterar den till
	// LocalDateTime. Sedan skriver den ut det användaren angett och
	// lägger till det till en arraylist.
	public void case1(BookingHandler bh, BufferedReader reader) throws IOException {
		String name = null;
		do {
			System.out.println("Ange ditt namn: ");
			name = reader.readLine();
		} while (name.equals(""));
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
			LocalDateTime b = formatter(dateTime);
			LocalDateTime e = formatter(dateEndTime);
			System.out.println("Från: " + b.format(formatter) + " \nTill: " + e.format(formatter));
			BookedTime c = bh.createBooking(name, b, e);
			bh.addBooking(c);
		} catch (DateTimeParseException e) {
			System.out.println("Du måste ange datum och tid enligt formatet som efterfrågas.\n");
		}
	}

	// Ändrar informationen för en angedd bokning.
	public void editor(BookingHandler bh, BufferedReader reader) throws IOException {
		System.out.println("Ange namnet för bokningen du vill redigera:");
		BookedTime b1 = bh.getBooking(reader.readLine());
		System.out.println(b1.getName());
		String svar = "";
		do {
			System.out.println("1. Redigera det bokade namnet.\n2. Redigera den bokade tiden.\n3. Gå till huvudmeny.");
			svar = reader.readLine();

			switch (svar) {

			case "1":
				System.out.println("Ange nytt namn:");
				String nameToFind = reader.readLine();
				if (!nameToFind.trim().equals(svar))
					b1.setName(nameToFind);
				break;
			case "2":
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
					LocalDateTime b = formatter(dateTime);
					LocalDateTime e = formatter(dateEndTime);
					System.out.println("Från: " + b.format(formatter) + " \nTill: " + e.format(formatter));
					if (!dateTime.trim().equals(svar))
						b1.setBeginTid(b);
					b1.setEndTime(e);
				} catch (DateTimeParseException e) {
					System.out.println("Du måste ange datum och tid enligt formatet som efterfrågas.\n");
				}
				break;
			default:
				break;

			}
		} while (!svar.equals("3"));
		System.out.println("Går tillbaka till huvudmeny..\n");

	}

}
