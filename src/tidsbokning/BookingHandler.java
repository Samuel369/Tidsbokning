package tidsbokning;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.*;

public class BookingHandler {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static ArrayList<BookedTime> bookingList;

	public BookingHandler() {
		bookingList = new ArrayList<BookedTime>();
	}

	// F�r att skapa nya bokningar.
	public BookedTime createBooking(String name, LocalDateTime beginTime, LocalDateTime endTime) {
		return new BookedTime(name, beginTime, endTime);
	}

	// F�r att l�gga till nya bokningar i arraylisten.
	public void addBooking(BookedTime b) {
		if (BookingHandler.check(b))
			bookingList.add(b);
		else
			System.out.println("Den angivna tiden �r upptagen, testa en annan tid.");
			return;
	}

	// F�r att kontrollera att inga tider �verskrider varandra.
	public static boolean check(BookedTime b) {
		LocalDateTime checkBegin = b.getBeginTime();
		LocalDateTime checkEnd = b.getEndTime();
		for (BookedTime s : bookingList)
			if (checkBegin.isAfter(s.getEndTime()) || checkEnd.isBefore(s.getBeginTime()))
				return true;
			else if (checkBegin.isBefore(s.getEndTime()) || checkEnd.isAfter(s.getBeginTime()))
				return false;
		return true;

	}
	
	public void removeBooking(BookedTime c) {
		bookingList.remove(c);
	}

	public BookedTime getBooking(String string) {
		for (BookedTime c : bookingList)
			if (c.getName().equals(string))
				return c;
		return null;
	}

	public ArrayList<BookedTime> getBooking() {
		return bookingList;
	}

}
