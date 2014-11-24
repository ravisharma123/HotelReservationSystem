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
public class comprehensiveReceipt implements receiptFormatter{
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
		String receipt = "";
		
		for (Room r: guest.getRoomList() )
		{
			int roomPrice = r.getPrice();
			total += roomPrice;
			receipt = "\nRoom " + r.getRoomNumber() + ": $" + roomPrice;
		}

		return receipt;
	}
	

	/**
	 * Displays the total payment
	 */
	public String formatFooter() {

		return "\n\n\t\tTOTAL DUE: $" + total;
	}
	
}
