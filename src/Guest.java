import java.io.Serializable;
import java.util.ArrayList;

/***********************************************************
 * The Guest of a hotel to make reservations
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 ***********************************************************/
public class Guest implements Serializable {
    private String username;
    private int userID;
    private ArrayList<Room> roomList;

    public Guest(int userID, String username) {
        this.userID = userID;
        this.username = username;

        roomList = new ArrayList<Room>();
    }

    /**
     * 
     * @return the Guest ID
     */
    public int getUserID() {
        return userID;
    }
	
    /**
	 * 
	 * @return the Guest name
	 */
    public String getUsername() {
        return username;
    }

    /**
     * Adds a room reservation to the
     * list of reservations that the Guest
     * has reserved 
     * @param addReservation is a Room
     */
    public void addToGuestReservations(Room addReservation) {
        roomList.add(addReservation);
    }

    /**
     * Removes a room reservation to the
     * list of reservations that the Guest
     * has reserved 
     * @param removeReservation is a Room
     */
    public void removeFromGuestReservations(Room removeReservation) {
        roomList.remove(removeReservation);
    }

    /**
     * Gets the list of rooms the Guest reserved
     * @return roomList is a list of rooms
     */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }
    
    /**
     * Creates a receipt for the
     * Guest for the reservations made
     * Can return one or all reservations
     * 
     * USES STRATEGY PATTERN
     * 
     * @param receiptType
     * @return A String of the receipt
     */
    public String getReceipt(ReceiptFormatter receiptType) {
    	return receiptType.formatHeader(this) + receiptType.receipt(this) + receiptType.formatFooter();
    }

}
