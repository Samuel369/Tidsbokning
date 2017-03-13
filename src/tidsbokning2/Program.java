package tidsbokning2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class Program {

	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	static BookingHandler<BookedTime> bh = new BookingHandler<BookedTime>();
	static TimeHandler th = new TimeHandler();
	static BarberHandler bah = new BarberHandler();
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static void main(String[] args) {
		try {
			String svar = "";
			do {
				bah.Barbers();
				System.out.println("1. Boka tid.\n" + "2. Se bokade tider.\n" + "3. Redigera bokning.\n"
						+ "4. Ta bort bokning.\n" + "5. Spara bokningar för en frisör.\n"
						+ "6. Ladda upp bokningar för en frisör.\n" + "7. Avsluta.");
				svar = reader.readLine();

				switch (svar) {
				// Skapar en bokad tid.
				case "1":
					System.out.println("Öppettider: 08:00 -> 18:00");
					th.case1(bh, bah, reader);
					break;
				// Skriver ut listan på bokna tider.
				case "2":
					try {
						String name = null;
						do {
							System.out.println("Ange frisör: ");
							name = reader.readLine().trim();
						} while (name.equals(""));
						TreeSet<BookedTime> barbersList = bah.getBarbersList(name);
						th.case2(barbersList);
					} catch (IOException e) {
						System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
					} catch (NullPointerException e) {
						System.out.println("Du måste ange ett korrekt namn. ");
					}
					break;
				// Redigerar en bokning från listan.
				case "3":
					System.out.println("Ange frisörens namn:");
					String r = reader.readLine();
					TreeSet<BookedTime> p = bah.getBarbersList(r);
					th.editor(bh, p);
					break;
				// Tar bort en bokning från listan.
				case "4":
					System.out.println("Ange frisörens namn:");
					String d = reader.readLine();
					TreeSet<BookedTime> o = bah.getBarbersList(d);
					bh.removeBooking(o);
					break;
				// Sparar en bokning i en ny fil.
				case "5":
					System.out.println("Ange frisörens namn:");
					String s = reader.readLine();
					TreeSet<BookedTime> b = bah.getBarbersList(s);
					bh.saveBookings(s, b);
					break;
				// Laddar upp bokningar från en fil.
				case "6":
					System.out.println("Ange frisörens namn:");
					String l = reader.readLine();
					TreeSet<BookedTime> t = bah.getBarbersList(l);
					bh.loadBookings(l, t);
					break;
				// Avslutar.
				case "7":
					System.out.println("Avslutar..");
					break;
				default:
					System.out.println("Det angivna menyvalet finns inte.\n");
					break;
				}
				// Avslutar programmet om man trycker in "7" i huvudmenyn.
			} while (!svar.equals("7"));
		} catch (IOException e) {
			System.out.println("Error :" + e.getClass().getSimpleName() + " : " + e.getMessage());
		}

	}

}
