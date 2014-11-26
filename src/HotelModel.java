import java.io.Serializable;
import java.text.SimpleDateFormat;
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
 * Version 2014-11-15
 *********************************************/
public class HotelModel implements Serializable {
    private ArrayList<Room> hotelRoomData;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<Room> filteredResults;
    private ArrayList<Guest> guestList;

    public HotelModel(ArrayList<Room> setHotelRoomData)
    {
        hotelRoomData = setHotelRoomData;
        guestList = new ArrayList<Guest>();
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
     public void updateToAddGuest(Guest addGuest)
    {
        guestList.add(addGuest);
         for(ChangeListener l: listeners)
        {
            l.stateChanged(new ChangeEvent(this));
        }
       
    }

    /**
     * The filtered data is a list of rooms that are
     * vacant for that day
     * 
     * @param checkInDate is the first day of the reservation
     * @param checkOutDate is the last day of the reservation
     * @param isLuxury is either a standard or luxury room
     */
    public void setFilteredData(Calendar checkInDate, Calendar checkOutDate, boolean isLuxury)
    {
        filteredResults.clear();
        
        ArrayList<Calendar> calendar = hotelRoomData.get(0).getDates(checkInDate, checkOutDate);
        boolean addRm = true;
        for (Room r: hotelRoomData) {
        	for (Calendar cal: calendar) {
        		if ( (r.getBookedDates().contains(cal)) || (r.isLuxury() != isLuxury) ) {
        			addRm = false;
        		}
        			
        	}
        	if (addRm) {
        		filteredResults.add(r);
        	}
        	addRm = true;
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
            if(guestList.get(i).getUserID() == setGuest.getUserID()) 
            {	currentGuest = guestList.get(i);	}
        }
        
        return currentGuest;
    }
    
    /**
	 * @return the guestList
	 */
	public ArrayList<Guest> getGuestList() {
		return guestList;
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
    public String getRoomInfoOnDay(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
    	String message = dateFormat.format( calendar.getTime() ) + "\n\n" + "Room Type" + "\tRoom Number" + "\tGuest Name" + "\n";
    	
    	for (Room r: hotelRoomData)	{
    		if(r.getBookedDates().contains(calendar)) {
    			Guest user = new Guest(1, "");
    			for (Guest g: guestList) {
    				if(g.getRoomList().contains(r)) {
    					user = g;
    				}
    			}
    			message += r.getType() + "\t" + r.getRoomNumber() + "\t" + user.getUsername() + "\n"; 
    		}
    	}
    	return message;
    }

    // new methods needed to work with guestPanel
    public int hasGuestID(int getGuestID) {
      int guestRecordFound = -1;
            for (int i = 0; i < guestList.size(); i++) {
                if (guestList.get(i).getUserID() == getGuestID) {
                    guestRecordFound = i;
                }
            }
            return guestRecordFound;
    }

    public String getUsername(int userID) {
        String name="";
        for (int i=0; i < guestList.size(); i++) {
                if(guestList.get(i).getUserID() == userID) {
                    name=guestList.get(i).getUsername();
                }
            }
        return name;
    }

    /**
     *  Shows the available rooms
     * @return ArrayList of Rooms
     */
    public ArrayList<Room> getAvailableRoomInfo() {
      return filteredResults;
    }
}
