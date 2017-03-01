package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;

public class BookingRemover {

	static BookingHandler bh = new BookingHandler();

	public static void removeBooking(BookingHandler bh, BufferedReader reader) throws IOException {

		System.out.println("Ange namnet för den bokade tiden du vill ta bort:");
		BookedTime c1 = bh.getBooking(reader.readLine());
		System.out.println(c1.getName());
		bh.removeBooking(c1);
	}

}
