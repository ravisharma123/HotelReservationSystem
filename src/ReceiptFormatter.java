/*********************************************************
 * For printing out the receipt for hotel
 * reservations
 * 
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-14
 ********************************************************/
public interface ReceiptFormatter {
	/**
	 * Formats the header of the receipt
	 * 
	 */
	String formatHeader(Guest guest);
	
	/**
	 * formats a line of the    	
	 */
	String receipt(Guest guest);

	/**
	 * Formats footer of the receipt  	
	 */
	String formatFooter();
}
