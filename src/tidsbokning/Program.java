package tidsbokning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Program {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BookingHandler bh = new BookingHandler();
	static TimeHandler th = new TimeHandler();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static void main(String[] args) throws IOException {

		String svar = "";
		do {
			System.out.println("1. Boka tid.\n2. Se bokade tider.\n3. Ta bort bokning.\n4. Avsluta.");
			svar = reader.readLine();

			switch (svar) {

			case "1":
				th.case1(bh, reader);
				break;
			// Skriver ut listan på bokna tider.
			case "2":
				ArrayList<BookedTime> lista = bh.getBooking();
				for (BookedTime s : lista)
					System.out.println(s.toString());
				break;
			// Tar bort en bokning från listan. 
			case "3":
				BookingRemover.removeBooking(bh, reader);
				break;
			default:
				break;
			}
			// Avslutar programmet om man trycker in "3" i huvudmenyn.
		} while (!svar.equals("4"));

	}

}
