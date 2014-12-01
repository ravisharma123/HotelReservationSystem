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
public class ComprehensiveReceipt implements ReceiptFormatter{
	private double total;

	/**
	 * 	Displays the user id and name
	 * 
	 * @param guest is the Guest 
	 * making the reservation
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
	 * Displays all reserved rooms for
	 * the guest
	 * 
	 * @param guest is the guest making the reservation
	 */
	public String receipt(Guest guest) {
		String receipt = "\nRoom\tRoom Price\tNumber of Days";
		
		for (Room room: guest.getRoomList() )
		{
			int roomPrice = room.getPrice();
			int days = room.getDays(room.getCheckInDate(), room.getCheckOutDate() );
			total += roomPrice * days;
			receipt += "\n" + room.getRoomNumber() + "\t$" + roomPrice + "\t" + days;
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
