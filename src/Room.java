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
    	int days = getDays(checkInDate, checkOutDate);
      bookedDates.add((Calendar)new GregorianCalendar(checkInDate.get(Calendar.YEAR), checkInDate.get(Calendar.MONTH), checkInDate.get(Calendar.DATE))); //adds the check in date
      for (int i = 1; i <= days; i++) {
         checkInDate.add(Calendar.DATE, 1);
         bookedDates.add((Calendar)new GregorianCalendar(checkInDate.get(Calendar.YEAR), checkInDate.get(Calendar.MONTH), checkInDate.get(Calendar.DATE)));
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
    	int days = getDays(checkInDate, checkOutDate);
    	
    	int loc = bookedDates.indexOf(checkInDate);

        // deletes start date through end date
		for (int i = 1; i <= days; i++) {
            bookedDates.remove(loc);
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
    	long milliSecPerDay = 86400000;   //86400000 = 1 day in milliseconds
    	long days = 1 + ( checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis() ) / milliSecPerDay;
    	return (int) days;
    }
}
