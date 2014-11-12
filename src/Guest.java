import java.util.ArrayList;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 */
public class Guest {
    private String userName;
    private int userID;
    private ArrayList<Room> reservationsMadeByGuest;
	
    public Guest(String setName, int setID) {
        userName = setName;
        userID = setID;
        reservationsMadeByGuest= new ArrayList<Room>();
    }

    public void setUserName(String setUserName) {
        userName=setUserName;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserID(int setUserID) {
        userID=setUserID;

    }

    public int getUserID() {
        return userID;
    }

    public void addToGuestReservations(Room addReservation) {
        reservationsMadeByGuest.add(addReservation);
    }

    public void removeFromGuestReservations(Room removeReservation) {
        reservationsMadeByGuest.remove(removeReservation);
    }

    public ArrayList<Room> getGuestReservations() {
        return reservationsMadeByGuest;
    }
}
