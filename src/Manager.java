import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 */
public class Manager {
    private String name;

    public Manager(String setName)
    {
        name = setName;
    }
  
    /**
     * Saves the hotel
     * reservations on to
     * a file
     */
    public void saveToFile(HotelRoomsDataModel hotelData)
    {
    	try
        {
           FileOutputStream fileOut = new FileOutputStream("Hotel_Data.txt");
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(hotelData);
           out.close();
           fileOut.close();
        }
    	catch(IOException i)
        {
            i.printStackTrace();
        }
    }
    
    /**
     * Loads the reservation
     * information from file
     * to a program
     */
    public HotelRoomsDataModel loadData()
    {
    	try
      	{
	        FileInputStream fileIn = new FileInputStream("Hotel_Data.txt");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        HotelRoomsDataModel hotelData = (HotelRoomsDataModel) in.readObject();
	        in.close();
	        fileIn.close();
	        return hotelData;
	    }
	    catch(IOException i)
	    {
	       i.printStackTrace();
	       return null;
	    }
	    catch(ClassNotFoundException c)
	    {
	       System.out.println("Employee class not found");
	       c.printStackTrace();
	       return null;
	    }
    }
}
