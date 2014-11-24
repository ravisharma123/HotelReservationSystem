import java.text.SimpleDateFormat;
import java.util.Calendar;

/*********************************************************
 * Format for the room receipt
 * creates a simple receipt
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-14
 ********************************************************/
public class simpleReceipt implements receiptFormatter{
	private double total;

	/**
	 * 	Displays the user id and name
	 */
	public String formatHeader(Guest guest) {
		this.total = 0;
		String header = "Name: " + guest.getUsername() + "\nUser ID: " + guest.getUserID() + "\n";
		
		Calendar calendar = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		return dateFormat.format( calendar.getTime() ) + "\n\n" + header;
	}

	/**
	 * Displays reserved rooms for
	 * the guest
	 * 
	 * @param guest is the guest making the reservation
	 */
	public String receipt(Guest guest) {
		int roomPrice = guest.getRoomList().get(guest.getRoomList().size()).getPrice();
		total += roomPrice;
		
		return "\nRoom " + guest.getRoomList().get(guest.getRoomList().size()).getRoomNumber() + ": $" + roomPrice;
	}
	

	/**
	 * Displays the total payment
	 */
	public String formatFooter() {

		return "\n\n\t\tTOTAL DUE: $" + total;
	}
	
}
