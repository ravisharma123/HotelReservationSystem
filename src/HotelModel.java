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
 * Version 2014-12-03
 *********************************************/
public class HotelModel implements Serializable {
    private ArrayList<Room> hotelRoomData;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<Room> filteredResults;
    private ArrayList<Guest> guestList;

    /**
     * Creates a hotel model.
     * @param setHotelRoomData the rooms in the hotel model
     */
    public HotelModel(ArrayList<Room> setHotelRoomData)
    {
        hotelRoomData = setHotelRoomData;
        guestList = new ArrayList<>();
        listeners = new ArrayList<>();
        filteredResults = new ArrayList<>();
    }


    /**
     * Gets a deep copy of the 
     * List of the rooms
     * @return A the rooms in the hotel
     */
    public ArrayList<Room> getData()
    {   return (ArrayList<Room>) hotelRoomData.clone();    }

    /**
     * Gets the filtered data.
     * @return the filtered data
     */
   // public ArrayList<Room> getFilteredData()
   // {    return filteredResults;    }

    /**
     * Attaches a change listener.
     * @param c the change listener
     */
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
     * @param cancelReservation is a room
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
     * Adds a guest.
     * @param addGuest the guest to add
     */
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
     * Gets the guest list.
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
                	if(!g.getRoomList().isEmpty())
                	{
                    	for (Room rm: g.getRoomList())
                    	{
                    		if (rm.getRoomNumber() == r.getRoomNumber())
                    		{	user = g;	}
                    	}
                	}
                }
                message += r.getType() + "\t" + r.getRoomNumber() + "\t" + user.getUsername() + "\n";
            }
            else
            {
            	message += r.getType() + "\t" + r.getRoomNumber() + "\tvacant\n";
            }
        }
        return message;
    }

    /**
     * Looks to see if the Guest is already
     * in the system
     * @param getGuestID the ID the user typed in
     * @return the location of the user if found, if not found returns -1
     */
    public int hasGuestID(int getGuestID) {
        int guestRecordFound = -1;
        for (int i = 0; i < guestList.size(); i++) {
            if (guestList.get(i).getUserID() == getGuestID) {
                return i;
            }
        }
        return guestRecordFound;
    }

    /**
     *  Shows the available rooms
     * @return ArrayList of Rooms
     */
    public ArrayList<Room> getAvailableRoomInfo() {
        return filteredResults;
    }
}