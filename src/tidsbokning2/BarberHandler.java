package tidsbokning2;

import java.util.TreeSet;

public class BarberHandler {

	public TreeSet<Barber> barberList;

	public BarberHandler() {
		this.barberList = new TreeSet<Barber>();
	}

	public Barber createBarber(String name, TreeSet<BookedTime> bookedTimes) {
		return new Barber(name, bookedTimes);
	}

	public void add(Barber b) {
		barberList.add(b);
		return;
	}

	public TreeSet<BookedTime> getBarbersList(String name) {
		TreeSet<BookedTime> List = null;
		for (Barber c : barberList)
			if ( c.getName().equals(name)) {
				List = c.getBookedTimes();
				return List;
			}
		return null;
	}

	public TreeSet<Barber> getBarbers() {
		return barberList;
	}

	public void Barbers() {
		TreeSet<BookedTime> andersList = new TreeSet<BookedTime>();
		Barber b1 = createBarber("Anders", andersList);
		TreeSet<BookedTime> mariasList = new TreeSet<BookedTime>();
		Barber b2 = createBarber("Maria", mariasList);
		TreeSet<BookedTime> sandrasList = new TreeSet<BookedTime>();
		Barber b3 = createBarber("Sandra", sandrasList);

		barberList.add( b1);
		barberList.add( b2);
		barberList.add( b3);
		return;
	}

}
