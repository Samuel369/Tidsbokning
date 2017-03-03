package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class TimeHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	// Omformatterar input från användaren till LocalDateTime.
	public LocalDateTime formatter(String time) {

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
		String dateYear = reader.readLine().trim();
		System.out.println("Ange månad [MM]: ");
		String dateMonth = reader.readLine().trim();
		System.out.println("Ange dag [DD]: ");
		String dateDay = reader.readLine().trim();
		String date = dateYear + "-" + dateMonth + "-" + dateDay;
		System.out.println("Ange starttid [HH:MM]: ");
		String time = reader.readLine().trim();
		String dateTime = date + " " + time;
		System.out.println("Ange sluttid [HH:MM]: ");
		String endTime = reader.readLine().trim();
		String dateEndTime = date + " " + endTime;
		String fBeginTime = "08:00";
		String fEndTime = "18:00";
		String fixedBeginTime = date + " " + fBeginTime;
		String fixedEndTime = date + " " + fEndTime;
		try {
			LocalDateTime b = formatter(dateTime);
			LocalDateTime e = formatter(dateEndTime);
			LocalDateTime bDT = formatter(fixedBeginTime);
			LocalDateTime eDT = formatter(fixedEndTime);
			BookedTime c = bh.createBooking(name, b, e);
			bh.addBooking(c, bDT, eDT);
			System.out.println("Från: " + b.format(formatter) + " \nTill: " + e.format(formatter));
		} catch (DateTimeParseException e) {
			System.out.println("Du måste ange datum och tid enligt formatet som efterfrågas.\n");
		}
	}

	// Ändrar informationen för en angedd bokning.
	public void editor(BookingHandler bh, BufferedReader reader) throws IOException {
		try {
			System.out.println("Ange namnet för bokningen du vill redigera:");
			BookedTime b1 = bh.getBooking(reader.readLine());
			System.out.println(b1.getName());
			String svar = "";
			do {
				System.out.println(
						"1. Redigera det bokade namnet.\n2. Redigera den bokade tiden.\n3. Gå till huvudmeny.\n");
				svar = reader.readLine();

				switch (svar) {

				case "1":
					String nameToFind = null;
					do {
						System.out.println("Ange nytt namn:");
						nameToFind = reader.readLine();
						if (!nameToFind.trim().equals(svar))
							b1.setName(nameToFind);
					} while (nameToFind.equals(""));
					break;
				case "2":
					System.out.println("Ange år [YYYY]: ");
					String dateYear = reader.readLine().trim();
					System.out.println("Ange månad [MM]: ");
					String dateMonth = reader.readLine().trim();
					System.out.println("Ange dag [DD]: ");
					String dateDay = reader.readLine().trim();
					String date = dateYear + "-" + dateMonth + "-" + dateDay;
					System.out.println("Ange starttid [HH:MM]: ");
					String time = reader.readLine().trim();
					String dateTime = date + " " + time;
					System.out.println("Ange sluttid [HH:MM]: ");
					String endTime = reader.readLine();
					String dateEndTime = date + " " + endTime;
					String fBeginTime = "08:00";
					String fEndTime = "18:00";
					String fixedBeginTime = date + " " + fBeginTime;
					String fixedEndTime = date + " " + fEndTime;
					try {
						LocalDateTime b = formatter(dateTime);
						LocalDateTime e = formatter(dateEndTime);
						LocalDateTime bDT = formatter(fixedBeginTime);
						LocalDateTime eDT = formatter(fixedEndTime);
						if (b.isBefore(bDT) || e.isAfter(eDT)) {
							System.out.println("Kan inte boka en tid när det är stängt!\n");
							break;
						}
						ArrayList<BookedTime> list = bh.getBooking();
						for (BookedTime s : list)
							if (b.isAfter(s.getEndTime()) || e.isBefore(s.getBeginTime())) {
								System.out.println("Från: " + b.format(formatter) + " \nTill: " + e.format(formatter));
							} else if (b.isBefore(s.getEndTime()) || e.isAfter(s.getBeginTime())) {
								System.out.println("Den angivna tiden är upptagen, testa en annan tid.\n");
								break;
							}
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
		} catch (NullPointerException e) {
			System.out.println("Du måste ange ett korrekt namn.");
		}
	}
	/*
	 * public LocalTime fixedBeginTime() { String beginTime = "08:00";
	 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	 * LocalTime startTime = LocalTime.parse(beginTime, formatter); return
	 * startTime; }
	 * 
	 * public LocalTime fixedEndTime() { String endTime = "18:00";
	 * DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	 * LocalTime stopTime = LocalTime.parse(endTime, formatter); return
	 * stopTime; }
	 * 
	 * public LocalDate dateFormatter(String date) {
	 * 
	 * DateTimeFormatter dateFormatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd"); LocalDate formatDate =
	 * LocalDate.parse(date, dateFormatter);
	 * 
	 * return formatDate; }
	 */
}
