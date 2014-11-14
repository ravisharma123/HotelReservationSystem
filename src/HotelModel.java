import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**********************************************
 * This Data Model holds the information
 * about the hotel including the rooms
 * 
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 *********************************************/
public class HotelModel {
    private ArrayList<Room> hotelRoomData;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<Room> filteredResults;
    private ArrayList<Guest> guestList;

    public HotelModel(ArrayList<Room> setHotelRoomData)
    {
        hotelRoomData = setHotelRoomData;
        listeners = new ArrayList<ChangeListener>();
        filteredResults = new ArrayList<Room>();
    }

    /**
     * Gets a deep copy of the 
     * List of the rooms
     * @return A the rooms in the hotel
     */
    public ArrayList<Room> getData()
    {   return (ArrayList<Room>) hotelRoomData.clone();    }

    public void addGuest(Guest guest)
    {  	guestList.add(guest);    }
    
    public ArrayList<Room> getFilteredData()
    {    return filteredResults;    }
    
    public void attach(ChangeListener c)
    {   listeners.add(c);    }


    /**
     * Adds dates to the room.  The room is reserved 
	 * from the check-in date to the check-out
     * date. 
     * 
     * @param addReservation is a room
     * @param checkInDate is the first day of the reservation
     * @param checkOutDate is the last day of the reservation
     */
    public void updateToAddReservation(Room addReservation, Calendar checkInDate, Calendar checkOutDate)
    {
        hotelRoomData.get( hotelRoomData.indexOf(addReservation) ).setBookedDates(checkInDate, checkOutDate);
        for(ChangeListener l: listeners) 
        {
            l.stateChanged( new ChangeEvent(this) );
        }
    }

    /**
     * Removes dates from the room.  The room
     * becomes vacant from the check-in date
     * to the check-out date
     * 
     * @param addReservation is a room
     * @param checkInDate is the first day of the reservation
     * @param checkOutDate is the last day of the reservation
     */
    public void updateToCancelReservation(Room cancelReservation, Calendar checkInDate, Calendar checkOutDate)
    {
        hotelRoomData.get( hotelRoomData.indexOf(cancelReservation) ).deleteBookedDates(checkInDate, checkOutDate);
        for(ChangeListener l: listeners)
        {
            l.stateChanged( new ChangeEvent(this) );
        }
    }

    /**
     * The filtered data is a list of rooms that are
     * vacant for that day
     * 
     * @param checkInDate is the first day of the reservation
     * @param checkOutDate is the last day of the reservation
     * @param typeOfRoom is either a standard or luxury room
     */
    public void setFilteredData(Calendar checkInDate, Calendar checkOutDate, String typeOfRoom)
    {
        filteredResults.removeAll(filteredResults);
        
        for(int i = 0; i < hotelRoomData.size(); i++)
        {
            if( ( !( ( hotelRoomData.get(i).getBookedDates().contains(checkInDate) ) || ( hotelRoomData.get(i).getBookedDates().contains(checkInDate) ) ) ) && ( hotelRoomData.get(i).getTypeOfRoom().equalsIgnoreCase(typeOfRoom) ) )
                filteredResults.add(hotelRoomData.get(i));
        }
        
        for(ChangeListener l: listeners)
        {
            l.stateChanged(new ChangeEvent(this));
        }
    }


    
    /**
     * Checks to see if the User is in the system.
     * if in the system the User can view
     * and use the system. If not the User
     * gets sent to create a User
     * 
     * @param setGuest the guest to log in
     */
    public Guest display(Guest setGuest)
    {
        Guest currentGuest = null;
        
        for(int i = 0; i < guestList.size(); i++)
        {
            if( guestList.get(i).getUserID() == setGuest.getUserID() ) 
            {
                currentGuest = guestList.get(i);
            }
        }
        
        return currentGuest; // returns the current guest
    }
    
    /**
     * Finds the reserved rooms on a specific date and
     * sends a string of information about the reserved
     * rooms.
     * 
     * e.g. 
     * Regular Room 1: Tom
     * Luxury Room 12: Jeff
     * 
     * @param calendar is the date for the manager view
     * @return is a string of all the rooms that are not
     * vacant.
     * 
     */
    public String getRoomInfoOnDay(Calendar calendar)
    {
    	String message = calendar.getTime().toString();
    	
    	for (Room r: hotelRoomData)
    	{
    		if( r.getBookedDates().contains(calendar)  )
    		{
    			Guest user = new Guest(null, 1);
    			for (Guest g: guestList)
    			{
    				if( g.getGuestReservations().contains(r) )
    				{	user = g;	}
    			}
    			message += r.getTypeOfRoom() + "Room " + r.getRmNum() + ": " + user.getUserName() + "\n"; //Regular Room 1: guest name
    		}
    	}
    	return message;
    }

}
