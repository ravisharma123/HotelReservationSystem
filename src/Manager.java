import java.io.*;
import java.util.Calendar;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-14
 */
public class Manager {
    public static final String FILENAME = "HotelModel.txt";
    private HotelModel hotelModel;

    public Manager(HotelModel hotelModel) {
        this.hotelModel = hotelModel;
    }

    /**
     * Saves the hotel model.
     * @return true on success, otherwise false
     */
    public boolean save() {
        if(hotelModel == null) {
            return false;
        }

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            objectOutputStream.writeObject(hotelModel);

            return true;
        }
        catch(FileNotFoundException e) {}
        catch(IOException e) {}

        return false;
    }

    /**
     * Loads the hotel model.
     * @return hotel model on success, otherwise null
     */
    public static HotelModel load() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILENAME))) {
            Object object = objectInputStream.readObject();

            return (HotelModel) object;
        }
        catch(FileNotFoundException e) {}
        catch(IOException e) {}
        catch(ClassNotFoundException e) {}

        return null;
    }

    /**
     * Gets the room info on a certain calendar day.
     * @param calendar the calendar
     * @return the room info
     */
    public String getRoomInfoOnDay(Calendar calendar) {
        return hotelModel.getRoomInfoOnDay(calendar);
    }
}
