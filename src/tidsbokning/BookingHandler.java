package tidsbokning;

import java.util.ArrayList;
import java.util.Scanner;
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BookingHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static ArrayList<BookedTime> bookingList;
	static TimeHandler th = new TimeHandler();

	public BookingHandler() {
		bookingList = new ArrayList<BookedTime>();
	}

	// För att skapa nya bokningar.
	public BookedTime createBooking(String name, LocalDateTime beginTime, LocalDateTime endTime) {
		return new BookedTime(name, beginTime, endTime);
	}

	// För att lägga till nya bokningar i arraylisten.
	public void addBooking(BookedTime b) {
		if (BookingHandler.check(b))
			bookingList.add(b);
		else
			System.out.println("Den angivna tiden är upptagen, testa en annan tid.\n");
		return;
	}

	// För att kontrollera att inga tider överskrider varandra.
	public static boolean check(BookedTime b) {
		LocalDateTime checkBegin = b.getBeginTime();
		LocalDateTime checkEnd = b.getEndTime();
		/*String beginTime = "08:00";
		String endTime = "18:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime startTime = LocalDateTime.parse(beginTime, formatter);
		LocalDateTime stopTime = LocalDateTime.parse(endTime, formatter);*/
		
		for (BookedTime s : bookingList)
			if (checkBegin.isAfter(s.getEndTime()) || checkEnd.isBefore(s.getBeginTime())
					/*|| checkBegin.isAfter(startTime) || checkEnd.isBefore(stopTime)*/);
			else if (checkBegin.isBefore(s.getEndTime()) || checkEnd.isAfter(s.getBeginTime())
					/*|| checkBegin.isBefore(startTime) || checkEnd.isAfter(stopTime)*/)
				return false;
		return true;

	}
	//Tar bort en bokning.
	public void removeBooking(BookedTime c) {
		bookingList.remove(c);
	}

	public BookedTime getBooking(String name) {
		for (BookedTime c : bookingList)
			if (c.getName().equals(name))
				return c;
		return null;
	}

	public ArrayList<BookedTime> getBooking() {
		return bookingList;
	}
	//Anropar metoden för att ta bort en bokning.
	public void removeBooking(BufferedReader reader) throws IOException {

		System.out.println("Ange namnet för den bokade tiden du vill ta bort:");
		String name = reader.readLine();
		BookedTime c1 = getBooking(name);
		try {
			System.out.println("Tar bort: " + c1.getName());
			removeBooking(c1);
		} catch (NullPointerException e) {
			System.out.println("Ett fel: " + e.getClass().getSimpleName() + " : " + e.getMessage());
		}
	}
	//Sparar bokningar i en ny fil.
	public void saveBookings(String b) {
		ArrayList<BookedTime> lista = getBooking();
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("Bookings", b), StandardCharsets.UTF_8,
				StandardOpenOption.CREATE);) {
			for (BookedTime s : lista) {
				System.out.println(s);
				writer.write(s.toFileFormat());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error. Du måste skriva in ett korrekt namn på filen.\n");
		}
	}
	//Laddar upp bokningar från en fil.
	public void loadBookings(String b) throws IOException {
		BookingHandler bh = new BookingHandler();
		try (Scanner scanner = new Scanner(new File("Bookings", b));) {
			while (scanner.hasNext()) {
				String s = scanner.nextLine();
				String[] arr = s.split(",");
				try {
					LocalDateTime b1 = th.formatter(arr[1]);
					LocalDateTime e = th.formatter(arr[2]);
					BookedTime c = bh.createBooking(arr[0], b1, e);
					bh.addBooking(c);
				} catch (DateTimeParseException e) {
					System.out.println("Fel format i filen.\n");
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Du måste ange det korrekta namnet på filen du vill använda.\n");
		}
	}
}
