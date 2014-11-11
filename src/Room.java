
import java.util.ArrayList;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ravi Sharma
 */
public class Room {
    private Calendar checkInDate;
    private Calendar checkOutDate;
    private ArrayList<Calendar> bookedDates;
    private double price;
    private String typeOfRoom;
    public Room(double setPrice,String setTypeOfRoom){
        price = setPrice;
        typeOfRoom=setTypeOfRoom;
        checkInDate=null;
        checkOutDate=null;
        bookedDates= new ArrayList<Calendar>();
    }
    public void setCheckInDate(Calendar setCheckInDate){
        checkInDate=setCheckInDate;
    }
    public Calendar getCheckInDate(){
        return checkInDate;
    }
    public void setCheckOutDate(Calendar setCheckOutDate){
        checkOutDate=setCheckOutDate;
    }
    public Calendar getCheckOutDate(){
        return checkOutDate;
    }
    public void setPrice(double setPrice){
        price = setPrice;
    }
    public double getPrice(){
        return price;
    }

    public void setTypeOfRoom(String setType){
        typeOfRoom=setType;
    }
    public String getTypeOfRoom(){
        return typeOfRoom;
    }
    public void setBookedDates(Calendar checkInDate, Calendar checkOutDate){
    /*
    add Calendar objects to the ArrayList bookedDates from the checkInDate up to checkOutDate
    */
    }
    public void deleteBookedDates(Calendar checkInDate, Calendar checkOutDate){
    /*
    remove the Calendar objects from the arraylist from the checkInDate up to the checkOutDate
    */
    }
    public ArrayList<Calendar> getBookedDates(){
        return bookedDates;
    }
}
