package tidsbokning2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookedTime implements Comparable<BookedTime>{

	private String name;
	private LocalDateTime beginTime;
	private LocalDateTime endTime;
	private String price;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public BookedTime(String name, LocalDateTime beginTime, LocalDateTime endTime) {
		this.name = name;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.price = "350kr";
	}

	public LocalDateTime getBeginTime() {
		return beginTime;
	}

	public void setBeginTid(LocalDateTime beginTime) {
		this.beginTime = beginTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + ": " + getBeginTime().format(formatter).toString() + "\nTill: "
				+ getEndTime().format(formatter).toString() + " : " + price + "\n";
	}
	public String toFileFormat() {
		return name + "," + getBeginTime().format(formatter).toString() + ","
				+ getEndTime().format(formatter).toString();
	}
	
	public boolean equals (Object o){
		BookedTime other = (BookedTime) o;
		return beginTime.toString().equalsIgnoreCase(other.beginTime.toString());
	}

	@Override
	public int compareTo(BookedTime o) {
		return beginTime.toString().compareToIgnoreCase(o.beginTime.toString());
	}

}
