package tidsbokning2;

import java.util.Scanner;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.*;
import java.time.format.DateTimeParseException;

public class BookingHandler<T> {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BarberHandler bah = new BarberHandler();
	static TimeHandler th = new TimeHandler();

	// Skapar nya bokningar.
	public BookedTime createBooking(String name, LocalDateTime beginTime, LocalDateTime endTime) {
		return new BookedTime(name, beginTime, endTime);
	}

	// Lägger till nya bokningar i arraylisten.
	public TreeSet<BookedTime> addBooking(BookedTime b, LocalDateTime bDT, LocalDateTime eDT,
			TreeSet<BookedTime> barberList) {
		if (BookingHandler.check(b, bDT, eDT, barberList)) {
			barberList.add(b);
		}
		return barberList;
	}

	// Kontrollerar att inga tider överskrider varandra.
	public static boolean check(BookedTime b, LocalDateTime bDT, LocalDateTime eDT, TreeSet<BookedTime> barberList) {
		LocalDateTime checkBegin = b.getBeginTime();
		LocalDateTime checkEnd = b.getEndTime();

		if (checkBegin.isBefore(bDT) || checkEnd.isAfter(eDT)) {
			System.out.println("Kan inte boka en tid när det är stängt!\n");
			return false;
		}
		for (BookedTime s : barberList)
			if (checkBegin.isAfter(s.getEndTime()) || checkEnd.isBefore(s.getBeginTime()))
				;
			else if (checkBegin.isBefore(s.getEndTime()) || checkEnd.isAfter(s.getBeginTime())) {
				System.out.println("Den angivna tiden är upptagen, testa en annan tid.\n");
				return false;
			}
		return true;

	}

	// Tar bort en bokning.
	public void removeBooking(BookedTime c, TreeSet<BookedTime> t) {
		t.remove(c);
	}

	public BookedTime getBooking(String name, TreeSet<BookedTime> t) {
		for (BookedTime c : t)
			if (c.getName().equals(name))
				return c;
		return null;
	}

	// Anropar metoden för att ta bort en bokning.
	public void removeBooking(TreeSet<BookedTime> r) {
		try {
			System.out.println("Ange namnet för den bokade tiden du vill ta bort:");
			String name = reader.readLine();
			BookedTime c1 = getBooking(name, r);
			System.out.println("Tar bort: " + c1.getName());
			removeBooking(c1, r);
		} catch (NullPointerException e) {
			System.out.println("Du måste ange ett korrekt namn för den bokning du vill ta bort.");
		} catch (IOException e) {
			System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
		}
	}

	// Sparar bokningar i en ny fil.
	public void saveBookings(String b, TreeSet<BookedTime> t) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("Bookings", b), StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);) {
			for (BookedTime s : t) {
				System.out.println(s);
				writer.write(s.toFileFormat());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Du måste ange ett korrekt namn på filen.\n");
		}
	}

	// Laddar upp bokningar från en fil.
	public void loadBookings(String b, TreeSet<BookedTime> t) {
		BookingHandler<BookedTime> bh = new BookingHandler<BookedTime>();
		try (Scanner scanner = new Scanner(new File("Bookings", b));) {
			while (scanner.hasNext()) {
				String s = scanner.nextLine();
				String[] arr = s.split(",");
				try {
					LocalDateTime b1 = th.formatter(arr[1]);
					LocalDateTime e = th.formatter(arr[2]);
					String fBeginTime = "08:00";
					String fEndTime = "18:00";
					String dateTime = arr[1].substring(0, 10);
					String fixedBeginTime = dateTime + " " + fBeginTime;
					String fixedEndTime = dateTime + " " + fEndTime;
					LocalDateTime bDT = th.formatter(fixedBeginTime);
					LocalDateTime eDT = th.formatter(fixedEndTime);
					BookedTime c = bh.createBooking(arr[0], b1, e);
					bh.addBooking(c, bDT, eDT, t);
				} catch (DateTimeParseException e) {
					System.out.println("Fel format i filen.\n");
					System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Du måste ange det korrekta namnet på filen du vill använda.\n");
		}
	}
}
