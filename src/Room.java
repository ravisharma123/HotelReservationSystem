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

    public Room(double setPrice,String setTypeOfRoom) {
        price = setPrice;
        typeOfRoom = setTypeOfRoom;
        checkInDate = null;
        checkOutDate = null;
        bookedDates = new ArrayList<Calendar>();
    }

    public void setCheckInDate(Calendar setCheckInDate) {
        checkInDate = setCheckInDate;
    }

    public Calendar getCheckInDate() {
        return checkInDate;
    }

    public void setCheckOutDate(Calendar setCheckOutDate) {
        checkOutDate = setCheckOutDate;
    }

    public Calendar getCheckOutDate() {
        return checkOutDate;
    }

    public void setPrice(double setPrice) {
        price = setPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setTypeOfRoom(String setType) {
        typeOfRoom = setType;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public void setBookedDates(Calendar checkInDate, Calendar checkOutDate) {
    /*
    add Calendar objects to the ArrayList bookedDates from the checkInDate up to checkOutDate
    */
    }

    public void deleteBookedDates(Calendar checkInDate, Calendar checkOutDate) {
    /*
    remove the Calendar objects from the arraylist from the checkInDate up to the checkOutDate
    */
    }

    public ArrayList<Calendar> getBookedDates() {
        return bookedDates;
    }
}
