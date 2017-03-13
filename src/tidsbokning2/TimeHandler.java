package tidsbokning2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.TreeSet;

public class TimeHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	static BarberHandler bah = new BarberHandler();

	// Omformatterar input från användaren till LocalDateTime.
	public LocalDateTime formatter(String time) {

		LocalDateTime formatDateTime = LocalDateTime.parse(time, formatter);

		return formatDateTime;
	}

	// Tar input från användaren och omformatterar den till
	// LocalDateTime. Sedan skriver den ut det användaren angett och
	// lägger till det till en barbers treeset.
	public void case1(BookingHandler<BookedTime> bh, BarberHandler bah, BufferedReader reader) {
		try {
			String name = null;
			do {
				System.out.println("Ange ditt namn: ");
				name = reader.readLine().trim();
			} while (name.equals(""));
			String name1 = null;
			do {
				System.out.println("Frisörerna du kan ange är Anders, Maria eller Sandra.\n" + "Ange frisör: ");
				name1 = reader.readLine().trim();
			} while (name1.equals(""));
			TreeSet<BookedTime> barbersList = bah.getBarbersList(name1);
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
			String dateEndTime = date + " " + time;
			String fBeginTime = "08:00";
			String fEndTime = "18:00";
			String fixedBeginTime = date + " " + fBeginTime;
			String fixedEndTime = date + " " + fEndTime;
			LocalDateTime b = formatter(dateTime);
			LocalDateTime formatDateTime = LocalDateTime.parse(dateEndTime, formatter);
			LocalDateTime e = formatDateTime.plusMinutes(45);
			LocalDateTime bDT = formatter(fixedBeginTime);
			LocalDateTime eDT = formatter(fixedEndTime);
			BookedTime c = bh.createBooking(name, b, e);
			bh.addBooking(c, bDT, eDT, barbersList);
		} catch (DateTimeParseException e) {
			System.out.println("Du måste ange datum och tid enligt formatet som efterfrågas.\n");
		} catch (IOException e) {
			System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
		} catch (NullPointerException e) {
			System.out.println("Du måste ange ett korrekt namn. ");
		}
	}

	// Ändrar informationen för en angedd bokning.
	public void editor(BookingHandler<BookedTime> bh, TreeSet<BookedTime> t) {
		try {
			System.out.println("Ange namnet för bokningen du vill redigera:");
			BookedTime b1 = bh.getBooking(reader.readLine(), t);
			System.out.println(b1.getName());
			String svar = "";
			do {
				System.out.println(
						"1. Redigera det bokade namnet.\n2. Redigera den bokade tiden.\n3. Gå till huvudmeny.\n");
				svar = reader.readLine();

				mainLoop: switch (svar) {

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
					String dateEndTime = date + " " + time;
					String fBeginTime = "08:00";
					String fEndTime = "18:00";
					String fixedBeginTime = date + " " + fBeginTime;
					String fixedEndTime = date + " " + fEndTime;
					try {
						LocalDateTime b = formatter(dateTime);
						LocalDateTime formatDateTime = LocalDateTime.parse(dateEndTime, formatter);
						LocalDateTime e = formatDateTime.plusMinutes(45);
						LocalDateTime bDT = formatter(fixedBeginTime);
						LocalDateTime eDT = formatter(fixedEndTime);
						if (b.isBefore(bDT) || e.isAfter(eDT)) {
							System.out.println("Kan inte boka en tid när det är stängt!\n");
							break;
						}
						for (BookedTime s : t)
							if (b.isAfter(s.getEndTime()) || e.isBefore(s.getBeginTime())) {
								System.out.println("Från: " + b.format(formatter) + " \nTill: " + e.format(formatter));
							} else if (b.isBefore(s.getEndTime()) || e.isAfter(s.getBeginTime())) {
								System.out.println("Den angivna tiden är upptagen, testa en annan tid.\n");
								break mainLoop;
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
			System.out.println("Återgår till huvudmeny..\n");
		} catch (NullPointerException e) {
			System.out.println("Du måste ange ett korrekt namn.");
		} catch (IOException e) {
			System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
		}
	}

	// Skriver ut listan på bokna tider.
	public void case2(TreeSet<BookedTime> barbersList) {
		try {
			String svar = "";
			do {
				System.out.println("1. Lista bokningar för en dag\n" + "2. Lista lediga tider för en dag\n"
						+ "3. Lista alla bokningar\n" + "4. Gå tillbaka till huvudmeny.");
				svar = reader.readLine();
				switch (svar) {
				case "1":
					System.out.println("Ange vilket år: [YYYY]");
					String year = reader.readLine().trim().substring(0, 4);
					System.out.println("Ange vilken månad: [MM]");
					String month = reader.readLine().trim().substring(0, 2);
					System.out.println("Ange vilken dag: [DD]");
					String day = reader.readLine().trim().substring(0, 2);
					String stringDate = year + "-" + month + "-" + day;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(stringDate, formatter);
					TreeSet<BookedTime> specificDayList = getDates(date, barbersList);
					for (BookedTime s : specificDayList)
						System.out.println(s);
					break;
				case "2":

					break;
				case "3":
					for (BookedTime s : barbersList)
						System.out.println(s.toString());
					break;
				case "4":
					System.out.println("Återgår till huvudmeny..\n");
					break;
				default:
					break;
				}
			} while (!svar.equals("4"));
		} catch (IOException e) {
			System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
		} catch (DateTimeParseException e) {
			System.out.println("Du måste ange datum och tid enligt formatet som efterfrågas.\n");
		}
	}

	// Jämför 2 datum.
	public TreeSet<BookedTime> getDates(LocalDate date, TreeSet<BookedTime> barbersList) {
		TreeSet<BookedTime> specificDayList = new TreeSet<BookedTime>();
		for (BookedTime c : barbersList) {
			int year = c.getBeginTime().getYear();
			int month = c.getBeginTime().getMonthValue();
			int day = c.getBeginTime().getDayOfMonth();
			int dateYear = date.getYear();
			int dateMonth = date.getMonthValue();
			int dateDay = date.getDayOfMonth();
			String listDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
			String dateDate = String.valueOf(dateYear) + "-" + String.valueOf(dateMonth) + "-"
					+ String.valueOf(dateDay);
			if (listDate.equals(dateDate))
				specificDayList.add(c);
		}
		return specificDayList;
	}

}
