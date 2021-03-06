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
			System.out.println(
					"1. Boka tid.\n2. Se bokade tider.\n3. Redigera bokning.\n4. Ta bort bokning.\n5. Spara bokningar.\n"
							+ "6. Ladda upp tidigare bokningar.\n7. Avsluta.");
			svar = reader.readLine();

			switch (svar) {
			// Skapar en bokad tid.
			case "1":
				System.out.println("Öppettider: 08:00 -> 18:00");
				th.case1(bh, reader);
				break;
			// Skriver ut listan på bokna tider.
			case "2":
				ArrayList<BookedTime> list = bh.getBooking();
				for (BookedTime s : list)
					System.out.println(s.toString());
				break;
			// Redigerar en bokning från listan.
			case "3":
				th.editor(bh, reader);
				break;
			// Tar bort en bokning från listan.
			case "4":
				bh.removeBooking(reader);
				break;
			// Sparar en bokning i en ny fil.
			case "5":
				System.out.println(
						"Ange namnet på filen du vill spara: (Om filen inte existerar redan så kommer den att skapas.)");
				String s = reader.readLine();
				bh.saveBookings(s);
				break;
			// Laddar upp bokningar från en fil. Fungerar inte just nu, ska fixas!
			case "6":
				System.out.println("Ange namnet på filen du vill ladda upp bokningar från:");
				String l = reader.readLine();
				bh.loadBookings(l);
				break;
			// Avslutar.
			case "7":
				break;
			default:
				System.out.println("Det angivna menyvalet finns inte.\n");
				break;
			}
			// Avslutar programmet om man trycker in "7" i huvudmenyn.
		} while (!svar.equals("7"));
		System.out.println("Avslutar..");

	}

}
