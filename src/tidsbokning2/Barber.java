package tidsbokning2;

import java.util.TreeSet;

public class Barber implements Comparable<Barber>{

	private String name;
	private TreeSet<BookedTime> bookedTimes; 
	/*
	public Barber(String name){
		this.bookedTimes = new BookingHandler<BookedTime>();
		this.name = name;
	}
	*/
	public Barber(String name, TreeSet<BookedTime> bookedTimes) {
		this.name = name;
		this.bookedTimes = bookedTimes;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public TreeSet<BookedTime> getBookedTimes() {
		return bookedTimes;
	}

	public void setBookedTimes(TreeSet<BookedTime> bookedTimes) {
		this.bookedTimes = bookedTimes;
	}

	@Override
	public String toString() {
		return name + " : " + bookedTimes;
	}
	
	public boolean equals (Object o){
		Barber other = (Barber) o;
		return name.equalsIgnoreCase(other.name);
	}
	
	@Override
	public int compareTo(Barber o) {
		return name.compareToIgnoreCase(o.name);
	}

}
