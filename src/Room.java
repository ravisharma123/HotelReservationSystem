import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*******************************************************
 * Holds information about a hotel room
 * 
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-14
 *******************************************************/
public class Room {
	private Calendar checkInDate;
	private Calendar checkOutDate;
	private ArrayList<Calendar> bookedDates;
	private int price;
	private boolean isLuxury;
	private int roomNumber;
	final static long MILLISECSPERDAY = 1000*60*60*24;

	public Room(boolean isLuxury, int roomNumber) {
		this.isLuxury = isLuxury;

		// set price based on isLuxury
		if(this.isLuxury) {
			price = 200;
		}
		else {
			price = 80;
		}

		this.roomNumber = roomNumber;

		checkInDate = null;
		checkOutDate = null;

		bookedDates = new ArrayList<>();

	}

	public Calendar getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Calendar checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Calendar getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Calendar checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public int getPrice() {
		return price;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public String getType() {
		if(isLuxury) {
			return "Luxury";
		}

		return "Regular";
	}

	public boolean isLuxury() {
		return isLuxury;
	}

	public ArrayList<Calendar> getBookedDates() {
		return bookedDates;
	}

	/**
	 * Adds the check-in to check-out days to the room
	 * signifying the room is not vacant on those days
	 * 
	 * @param checkInDate the room check in date
	 * @param checkOutDate the room check out date
	 */
	public void setBookedDates(Calendar checkInDate, Calendar checkOutDate) {
		ArrayList<Calendar> dates = getDates(checkInDate, checkOutDate);
		
		for (Calendar cal: dates) {
			bookedDates.add(cal);
		 }		
	}

	/**
	 * Removes the check-in to check-out days to
	 * the room making the room vacant
	 * 
	 * @param checkInDate the room check in date
	 * @param checkOutDate the room check out date
	 */
	public void deleteBookedDates(Calendar checkInDate, Calendar checkOutDate) {
		ArrayList<Calendar> dates = getDates(checkInDate, checkOutDate);

		for (Calendar cal: dates)
		{
			if ( bookedDates.contains(cal) )
			{		bookedDates.remove( bookedDates.indexOf(cal) );		}
		}
	}

	/**
	 * gets the number of days the room is set for
	 * e.g. 11/13/2014 to 11/14/2014 is 2 days
	 * 
	 * @param checkInDate the room check in date
	 * @param checkOutDate the room check out date
	 * @return the number of days between the dates (including the date)
	 */
	public int getDays(Calendar checkInDate, Calendar checkOutDate) {
		long days = 1 + ( checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis() ) / MILLISECSPERDAY;
		return (int) days;
	}
	
	/**
	 * Gets all the days from
	 * check-in to check-out
	 * 
	 * @param checkInDate
	 * @param checkOutDate
	 * @return list of dates
	 */
	public ArrayList<Calendar> getDates(Calendar checkInDate, Calendar checkOutDate) {
		ArrayList<Calendar> reservationDates = new ArrayList<Calendar>();
		Calendar cal = (Calendar) checkInDate.clone();
		
		for (long i = checkInDate.getTimeInMillis(); i <= checkOutDate.getTimeInMillis(); i = i + MILLISECSPERDAY)
		{
			Calendar newCal = (Calendar) cal.clone();
			reservationDates.add(newCal);
			cal.setTimeInMillis(cal.getTimeInMillis() + MILLISECSPERDAY);
		}
		
		return reservationDates;
	}
}
