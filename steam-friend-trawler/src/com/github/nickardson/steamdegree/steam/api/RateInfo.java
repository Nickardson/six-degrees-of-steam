package com.github.nickardson.steamdegree.steam.api;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RateInfo implements Serializable {
	private static final long serialVersionUID = -4512243685632040538L;
	
	private Date countedDate;
	private int calls;
	
	public RateInfo() {
	}
	
	public RateInfo(Date date, int calls) {
		this.countedDate = date;
		this.calls = calls;
	}

	public void countup() {
		Calendar counted = new GregorianCalendar();
		counted.setTime(countedDate);
		Calendar now = new GregorianCalendar();
		
		// If it is a new day, accounting for daylight savings, etc
		if (counted.get(Calendar.YEAR) != now.get(Calendar.YEAR) || counted.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR)) {
			// reset the call count and counted time.
			System.out.println("Resetting API call count as it is a new day.");
			calls = 0;
			countedDate = new Date();
		} else {
			calls++;
		}
	}
	
	public Date getCountedDate() {
		return countedDate;
	}

	public void setCountedDate(Date countedDate) {
		this.countedDate = countedDate;
	}

	public int getCalls() {
		return calls;
	}

	public void setCalls(int calls) {
		this.calls = calls;
	}

	@Override
	public String toString() {
		return "RateInfo ["
				+ (countedDate != null ? "countedDate=" + countedDate + ", "
						: "") + "calls=" + calls + "]";
	}
}
