import java.util.ArrayList;

/***********************************************************
 * The Guest of a hotel to make reservations
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 ***********************************************************/
public class Guest {
    private String username;
    private int userID;
    private ArrayList<Room> roomList;

	
    public Guest(int userID, String username) {
        this.userID = userID;
        this.username = username;

        roomList = new ArrayList<>();
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void addToGuestReservations(Room addReservation) {
        roomList.add(addReservation);
    }

    public void removeFromGuestReservations(Room removeReservation) {
        roomList.remove(removeReservation);
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
    
    //uses strategy pattern
    public String getReceipt(receiptFormatter receiptType) {
    	return receiptType.formatHeader(this) + receiptType.receipt(this) + receiptType.formatFooter();
    }

}
