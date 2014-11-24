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

		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
		Calendar calendar = Calendar.getInstance();
		String header = df.format( calendar.getTime() );
		
		header += "\n\nName: " + guest.getUsername() + "\nUser ID: " + guest.getUserID() + "\n";

		return  header;
	}

	/**
	 * Displays reserved rooms for
	 * the guest
	 * 
	 * @param guest is the guest making the reservation
	 */
	public String receipt(Guest guest) {
		int milliPerDay = 1000 * 60 * 60 * 24;
		Room room = guest.getRoomList().get(guest.getRoomList().size()-1);
		int roomPrice = room.getPrice();
		int days = 1 + (int)( (room.getCheckOutDate().getTimeInMillis() - room.getCheckInDate().getTimeInMillis()) / (milliPerDay) );
		total += roomPrice * days;
		//System.out.println(room.getCheckOutDate().getTimeInMillis() + " - " + room.getCheckInDate().getTimeInMillis() + " = " + milliPerDay);
		return "\nRoom Price\nRoom " + room.getRoomNumber() + ": $" + roomPrice;
	}
	

	/**
	 * Displays the total payment
	 */
	public String formatFooter() {

		return "\n\n\t\tTOTAL DUE: $" + total;
	}
	
}
