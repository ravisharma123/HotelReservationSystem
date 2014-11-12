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
    ArrayList<Room> hotelRoomData;
    ArrayList<ChangeListener> listeners;
    ArrayList<Room> filteredResults;
    public HotelRoomsDataModel(ArrayList<Room> setHotelRoomData){
        hotelRoomData=setHotelRoomData;
        listeners = new ArrayList<ChangeListener>();
        filteredResults = new ArrayList<Room>();
    }
    public ArrayList<Room> getData(){
        return (ArrayList<Room>)(hotelRoomData.clone());
    }
    public void attach(ChangeListener c){
        listeners.add(c);
    }

    public void updateToAddReservation(Room addReservation, Calendar checkInDate, Calendar checkOutDate){
        hotelRoomData.get(hotelRoomData.indexOf(addReservation)).setBookedDates(checkInDate, checkOutDate);
        for(ChangeListener l: listeners){
            l.stateChanged(new ChangeEvent(this));
        }
    }
    public void updateToCancelReservation(Room cancelReservation, Calendar checkInDate, Calendar checkOutDate){
        hotelRoomData.get(hotelRoomData.indexOf(cancelReservation)).deleteBookedDates(checkInDate, checkOutDate);
        for(ChangeListener l: listeners){
            l.stateChanged(new ChangeEvent(this));
        }
    }
    public void setFilteredData(Calendar checkInDate, Calendar checkOutDate, String typeOfRoom){
        filteredResults.removeAll(filteredResults);
        for(int i=0;i<hotelRoomData.size();i++){
            if(!hotelRoomData.get(i).getBookedDates().contains(checkInDate)&&hotelRoomData.get(i).getTypeOfRoom().equalsIgnoreCase(typeOfRoom))
                filteredResults.add(hotelRoomData.get(i));
        }
        for(ChangeListener l: listeners){
            l.stateChanged(new ChangeEvent(this));
        }
    }
    public  ArrayList<Room> getFilteredData(){
        return filteredResults;
    }

}
