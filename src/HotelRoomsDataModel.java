import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 */
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

    public ArrayList<Room> getData()
    {
        return (ArrayList<Room>) hotelRoomData.clone();
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
    
    public void display(Guest setGuest)
    {
        Guest currentGuest = null;
        
        for(int i = 0; i < guests.size(); i++)
        {
            if( guests.get(i).getUserID() == setGuest.getUserID() ) //make that int variable userID read above comment to see how
            {
                currentGuest = guests.get(i);
            }
        }
        
        if( currentGuest != null )
        {
            guestView.display(currentGuest);
        }
        
        else
        {
            guestView.displayForFirstTimeUser();
        }

        guest = setGuest;
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
