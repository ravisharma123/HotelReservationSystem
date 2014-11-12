import java.util.ArrayList;

/**
 * Solution to group project 2 for CS151-01.
 * Copyright(C) Luke Sieben, Nathan Kong, and Ravi Sharma
 * Version 2014-11-11
 */
public class HotelReservationSystemTester {
    public static ArrayList<Guest> guests;
    public static void main(String[]args){
        guests = new ArrayList<Guest>();
        Manager manager = new Manager("Bob");
        ArrayList<Room> rooms = new ArrayList<Room>();
        //make all 20 rooms and add to the arraylist rooms. Make 10 regular and 10 luxuary
        HotelRoomsDataModel dataOfRooms = new HotelRoomsDataModel(rooms);
        GuestView guestView = new GuestView(dataOfRooms);
        ManagerView managerView = new ManagerView(dataOfRooms);
        dataOfRooms.attach(guestView);
        dataOfRooms.attach(managerView);
        String typeOfUser="";
    /*Use GUI to display two buttons Guest and User depending on which is clicked set the String variable typeOfUser to the appropriate
      String so if Guest button is clicked then typeOfUser="guest", if Manager button is clicked then typeOfUser="manager"
    */
        if(typeOfUser.equalsIgnoreCase("guest")){
        /*ask for user id you can use a JTextField to get the user id and store it in a variable like this:
          JTextField getUserID = new JTextField; ... int userID = Integer.parseInt(getUserID.getText()); then use 
          the userID variable to look throught the ArrayList of guests and check if that user exists.
        */

            Guest currentGuest=null;
            for(int i=0;i<guests.size();i++){
                if(guests.get(i).getUserID()==userID){ //make that int variable userID read above comment to see how
                    currentGuest = guests.get(i);
                }
            }
            if(currentGuest!=null){
                guestView.display(currentGuest);
            }
            else{
                guestView.displayForFirstTimeUser();
            }

        }
        else{
            managerView.diplay();
        }

    }
}
