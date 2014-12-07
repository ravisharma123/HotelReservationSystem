/*********************************************************
 * For printing out the receipt for hotel
 * reservations
 * 
 * 
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-14
 ********************************************************/
/*Interface for Strategy Pattern --> this interface is called the strategy
  The interface/strategy defines three requirements that the concrete strategies must fulfill
  in their own way.
*/

public interface ReceiptFormatter {
	/**
	 * creates the header of the receipt
	 * 
	 */
	String formatHeader(Guest guest);
	
	/**
	 * creates a receipt of reservation info    	
	 */
	String receipt(Guest guest);

	/**
	 * creates footer of the receipt including price  	
	 */
	String formatFooter();
}
