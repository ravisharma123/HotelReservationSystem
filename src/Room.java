import java.util.ArrayList;
import java.util.Calendar;

/*******************************************************
 * Holds information about a hotel room
 * 
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 *******************************************************/
public class Room {
    private Calendar checkInDate;
    private Calendar checkOutDate;
    private ArrayList<Calendar> bookedDates;
    private double price;
    private String typeOfRoom;

    public Room(double setPrice,String setTypeOfRoom)
    {
        price = setPrice;
        typeOfRoom = setTypeOfRoom;
        checkInDate = null;
        checkOutDate = null;
        bookedDates = new ArrayList<Calendar>();
    }

    public void setCheckInDate(Calendar setCheckInDate)
    {   checkInDate = setCheckInDate;    }

    public Calendar getCheckInDate()
    {    return checkInDate;   }

    public void setCheckOutDate(Calendar setCheckOutDate)
    {  checkOutDate = setCheckOutDate;    }

    public Calendar getCheckOutDate()
    {   return checkOutDate;   }

    public void setPrice(double setPrice)
    {    price = setPrice;   }

    public double getPrice()
    {   return price;   }

    public void setTypeOfRoom(String setType)
    {    typeOfRoom = setType;    }

    public String getTypeOfRoom()
    { 	return typeOfRoom;   }
    
    public ArrayList<Calendar> getBookedDates()
    {	return bookedDates;   }

    /**
     * Adds the check-in to check-out days to the room
     * signifying the room is not vacant on those days
     * 
     * @param checkInDate the room check in date
     * @param checkOutDate the room check out date
     */
    public void setBookedDates(Calendar checkInDate, Calendar checkOutDate)
    {  	
    	int days = getDays(checkInDate, checkOutDate);
    	
    	for (int i = 1; i <= days; i++)
    	{
    		bookedDates.add(checkInDate); //adds the check in date
    		checkInDate.add(Calendar.DATE, 1); //increments the date by one day
    	}
    }

    /**
     * Removes the check-in to check-out days to
     * the room making the room vacant
     * 
     * @param checkInDate the room check in date
     * @param checkOutDate the room check out date
     */
    public void deleteBookedDates(Calendar checkInDate, Calendar checkOutDate)
    {
    	int days = getDays(checkInDate, checkOutDate);
    	
    	int loc = bookedDates.indexOf(checkInDate);
    	
		for (int i = 1; i <= days; i++)
		{	bookedDates.remove(loc);	}  //deletes start date through end date

    }

    /**
     * gets the number of days the room is set for
     * e.g. 11/13/2014 to 11/14/2014 is 2 days
     * 
     * @param checkInDate the room check in date
     * @param checkOutDate the room check out date
     * @return the number of days between the dates (including the date)
     */
    public int getDays(Calendar checkInDate, Calendar checkOutDate)
    {
    	long milliSecPerDay = 86400000;   //86400000 = 1 day in milliseconds
    	long days = ( checkOutDate.getTimeInMillis() - checkInDate.getTimeInMillis() ) / milliSecPerDay;
    	return (int) days + 1;
    }
}
