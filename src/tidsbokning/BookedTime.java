package tidsbokning;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookedTime {

	private String name;
	private LocalDateTime beginTime;
	private LocalDateTime endTime;
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public BookedTime(String name, LocalDateTime beginTime, LocalDateTime endTime) {
		this.name = name;
		this.beginTime = beginTime;
		this.endTime = endTime;
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
		return name + ": " + getBeginTime().format(formatter).toString() + " -> "
				+ getEndTime().format(formatter).toString();
	}

}
