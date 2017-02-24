package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;

public class BookingRemover {

	static BookingHandler bh = new BookingHandler();
	
	//Fungerar inte just nu.
	public static void removeBooking(BookingHandler bh, BufferedReader reader) throws IOException {

		System.out.println("Enter the name of the contact you wish to delete:");
		BookedTime c1 = bh.getBooking(reader.readLine());
		System.out.println(c1.getName());
		bh.removeBooking(c1);
	}

}
