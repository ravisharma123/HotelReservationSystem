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
public class HotelRoomsDataModel {
    private ArrayList<Room> hotelRoomData;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<Room> filteredResults;
    private ArrayList<Guest> guests;

    public HotelRoomsDataModel(ArrayList<Room> setHotelRoomData)
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
    {
        return (ArrayList<Room>) hotelRoomData.clone();
    }

    public void addGuest(Guest guest)
    {
    	guests.add(guest);
    }
    
    public void attach(ChangeListener c)
    {
        listeners.add(c);
    }


    public void updateToAddReservation(Room addReservation, Calendar checkInDate, Calendar checkOutDate)
    {
        hotelRoomData.get( hotelRoomData.indexOf(addReservation) ).setBookedDates(checkInDate, checkOutDate);
        for(ChangeListener l: listeners) 
        {
            l.stateChanged( new ChangeEvent(this) );
        }
    }

    public void updateToCancelReservation(Room cancelReservation, Calendar checkInDate, Calendar checkOutDate)
    {
        hotelRoomData.get( hotelRoomData.indexOf(cancelReservation) ).deleteBookedDates(checkInDate, checkOutDate);
        for(ChangeListener l: listeners)
        {
            l.stateChanged( new ChangeEvent(this) );
        }
    }

    public void setFilteredData(Calendar checkInDate, Calendar checkOutDate, String typeOfRoom)
    {
        filteredResults.removeAll(filteredResults);
        
        for(int i = 0; i < hotelRoomData.size(); i++)
        {
            if( ( !hotelRoomData.get(i).getBookedDates().contains(checkInDate) ) && ( hotelRoomData.get(i).getTypeOfRoom().equalsIgnoreCase(typeOfRoom) ) )
                filteredResults.add(hotelRoomData.get(i));
        }
        
        for(ChangeListener l: listeners)
        {
            l.stateChanged(new ChangeEvent(this));
        }
    }

    public ArrayList<Room> getFilteredData()
    {
        return filteredResults;
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
        
        for(int i = 0; i < guests.size(); i++)
        {
            if( guests.get(i).getUserID() == setGuest.getUserID() ) 
            {
                currentGuest = guests.get(i);
            }
        }
        
        return currentGuest; // returns the current guest
    }
    
    /**************************
     * for manager
     */
    //save ()
    
    //load()
    
    //get Room info on day (calendar)
    //use filter for luxury and regular
    //send viewer the string "Regular: Room 1: guest Name" // one string will all the rooms

}
